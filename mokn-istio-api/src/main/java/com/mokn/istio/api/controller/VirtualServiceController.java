package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.*;
import com.mokn.istio.api.model.k8s.virtualservice.VirtualService;
import com.mokn.istio.api.service.db.ResourceTemplateService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/virtualservice")
public class VirtualServiceController {

    @Autowired
    private VirtualServiceService virtualServiceService;
    @Autowired
    private ResourceTemplateService resourceTemplateService;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<VirtualServiceDomain> list(@RequestParam(value = "namespace",required = false) String namespace){
        JsonResult<VirtualServiceDomain> result=new JsonResult<>();
        namespace=namespace==null?"":namespace;
        return result.success(virtualServiceService.listVirtualServiceDomain(namespace));
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(virtualServiceService.delete(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody VirtualServiceCreateRequest request){
        JsonResult result=new JsonResult();
        if(request.getName()==null || request.getName().equals("")){
            return result.fail("名称不能为空");
        }
        if(request.getNamespace()==null || request.getNamespace().equals("")){
            return result.fail("命名空间不能为空");
        }
        if(request.getHost()==null || request.getHost().equals("")){
            return result.fail("Host不能为空");
        }
        if(request.getCreateType().equals("default")){
            if(request.getSubset()==null || request.getSubset().equals("")){
                return result.fail("版本Subset不能为空");
            }
        }else if(request.getCreateType().equals("gateway")){

            if(request.getRouteNumber()==null){
                return result.fail("路由端口不能为空");
            }
        }else{
            return result.fail("类型不存在");
        }

        VirtualServiceDomain domain=virtualServiceService.getVirtualServiceDomain(request.getNamespace(),request.getName());
        if(domain!=null){
            return result.fail("创建失败，已存在");
        }

        if(request.getCreateType().equals("default")){
            String template=resourceTemplateService.getByResource("VirtualService_Create_Default").getTemplate().trim();
            template=template.replace("{{.NAME}}",request.getName())
                    .replace("{{.ISTIO_VERSION}}",request.getSubset())
                    .replace("{{.ISTIO_VERSION_TYPE}}","VirtualService_Default")
                    .replace("{{.HOST}}",request.getName())
                    .replace("{{.SUBSET}}",request.getSubset());
            if(virtualServiceService.post(request.getNamespace(),request.getName(),template)){
                return result.success("创建成功");
            }else{
                return result.fail("创建失败");
            }
        }else{
            String template=resourceTemplateService.getByResource("VirtualService_Create_Gateway").getTemplate().trim();
            template=template.replace("{{.NAME}}",request.getName())
                    .replace("{{.GATEWAY_NAME}}",request.getGatewayName())
                    .replace("{{.ROUTE_HOST}}",request.getRouteHost())
                    .replace("{{.ROUTE_PORT}}",request.getRouteNumber().toString())
                    .replace("{{.HOST}}",request.getHost())
                    .replace("{{.ISTIO_VERSION_TYPE}}","VirtualService_Gateway");
            if(virtualServiceService.post(request.getNamespace(),request.getName(),template)){
                return result.success("创建成功");
            }else{
                return result.fail("创建失败");
            }
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/fail/set")
    public JsonResult set(@RequestBody VirtualServiceDomain request){
        JsonResult result=new JsonResult();
        if(request.getOpenFault()==null || request.getOpenFaultDelay()==null || request.getOpenFaultAbort()==null){
            return result.fail("未开启");
        }
        if(!request.getOpenFaultDelay() && !request.getOpenFaultAbort()){
            request.setOpenFault(false);
        }else{
            request.setOpenFault(true);
        }
        if(request.getOpenFault()){
            if(request.getName()==null || request.getName().equals("")){
                return result.fail("名称不能为空");
            }
            if(request.getNamespace()==null || request.getNamespace().equals("")){
                return result.fail("命名空间不能为空");
            }
            if(request.getOpenFaultDelay()){
                if(request.getFaultDelayFixedDelay()==null || request.getFaultDelayFixedDelay().equals("")){
                    return result.fail("faultDelayFixedDelay不能为空");
                }
                if(request.getFaultDelayPercent()==null){
                    return result.fail("faultDelayPercent不能为空");
                }
                if(request.getFaultDelayPercent()<0 || request.getFaultDelayPercent()>100){
                    return result.fail("faultDelayPercent值在0-100之间");
                }
            }
            if(request.getOpenFaultAbort()){
                if(request.getFaultAbortHttpStatus()==null){
                    return result.fail("faultAbortHttpStatus不能为空");
                }
                if(request.getFaultAbortPercent()==null){
                    return result.fail("faultAbortPercent不能为空");
                }
                if(request.getFaultAbortPercent()<0 || request.getFaultAbortPercent()>100){
                    return result.fail("faultAbortPercent值在0-100之间");
                }
            }
            VirtualService virtualService=virtualServiceService.getVirtualService(request.getNamespace(),request.getName());
            if(virtualService==null
                    || virtualService.getMetadata().getLabels()==null
                    || virtualService.getMetadata().getLabels().getIstioVersionType()==null
                    || !virtualService.getMetadata().getLabels().getIstioVersionType().equals("VirtualService_Default")
                    || virtualService.getSpec()==null
                    || virtualService.getSpec().getHttp()==null
                    || virtualService.getSpec().getHttp().size()!=1){
                return result.fail("斩不支持灰度版本");
            }
        }

        if(virtualServiceService.putFault(request.getNamespace(),request.getName(),request)){
            return result.success("设置成功");
        }
        return result.fail("设置失败");
    }
}
