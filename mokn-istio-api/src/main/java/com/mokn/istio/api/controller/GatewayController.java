package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.*;
import com.mokn.istio.api.service.db.ResourceTemplateService;
import com.mokn.istio.api.service.k8s.GatewayService;
import com.mokn.istio.api.service.k8s.NamespaceService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private NamespaceService namespaceService;
    @Autowired
    private ResourceTemplateService resourceTemplateService;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<GatewayDomain> listDeploymentDomain(@RequestParam(value = "namespace",required = false) String namepspace){
        JsonResult<GatewayDomain> result=new JsonResult<>();
        namepspace=namepspace==null?"default":namepspace;
        namepspace=namepspace==""?"default":namepspace;
        return result.success(gatewayService.listDomain(namepspace));
    }

    @LoginPassport
    @GetMapping(value = "/all")
    public JsonResult<GatewayDomain> listAll(){
        JsonResult<GatewayDomain> result=new JsonResult<>();
        List<GatewayDomain> domains=new ArrayList<>();
        for(NamespaceDomain namespace:namespaceService.listNamespaceDomain()){
            for(GatewayDomain gate:gatewayService.listDomain(namespace.getName())) {
                domains.add(gate);
            }
        }

        return result.success(domains);
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult deleteDeployment(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(request.getNamespace().equals("kube-system") || request.getNamespace().equals("istio-system") || request.getNamespace().equals("kube-public")){
            return result.fail("系统命名空间下的资源不能删除");
        }
        if(gatewayService.delete(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody GatewayCreateRequest request){
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
        if(request.getPort()==null || request.getPort()<=0){
            return result.fail("端口不能小于1");
        }


        GatewayDomain domain=gatewayService.getDomain(request.getNamespace(),request.getName());
        if(domain!=null){
            return result.fail("创建失败，已存在");
        }

        String template=resourceTemplateService.getByResource("Gateway_Create_Default").getTemplate().trim();
        template=template.replace("{{.NAME}}",request.getName())
                .replace("{{.HOST}}",request.getHost())
                .replace("{{.PORT_NAME}}","http")
                .replace("{{.PORT_PORT}}",request.getPort().toString())
                .replace("{{.PROT_PROTOCOL}}","HTTP");
        if(gatewayService.post(request.getNamespace(),request.getName(),template)){
            return result.success("创建成功");
        }else{
            return result.fail("创建失败");
        }
    }
}
