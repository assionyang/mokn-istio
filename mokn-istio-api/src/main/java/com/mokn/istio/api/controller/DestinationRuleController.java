package com.mokn.istio.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.db.ResourceTemplate;
import com.mokn.istio.api.model.domain.DestinationRuleDomain;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceAndNameAndAppRequest;
import com.mokn.istio.api.service.db.ResourceTemplateService;
import com.mokn.istio.api.service.k8s.DestinationRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/destinationrule")
public class DestinationRuleController {

    @Autowired
    private DestinationRuleService destinationRuleService;
    @Autowired
    private ResourceTemplateService resourceTemplateService;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<DestinationRuleDomain> list(@RequestParam(value = "namespace",required = false) String namespace){
        JsonResult<DestinationRuleDomain> result=new JsonResult<>();
        namespace=namespace==null?"default":namespace;
        namespace=namespace==""?"default":namespace;

        return result.success(destinationRuleService.listDomain(namespace));
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(destinationRuleService.delete(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(request.getName()==null || request.getName().equals("")){
            return result.fail("名称不能为空");
        }
        if(request.getNamespace()==null || request.getNamespace().equals("")){
            return result.fail("命名空间不能为空");
        }
        if(request.getVersion()==null || request.getVersion().equals("")){
            return result.fail("版本号不能为空");
        }

        String template=resourceTemplateService.getByResource("DestinationRule_Create_Default").getTemplate().trim();
        template=template.replace("{{.HOST}}",request.getName())
                .replace("{{.SUBSET}}",request.getVersion());
        DestinationRuleDomain destinationRuleDomain=destinationRuleService.getDomain(request.getNamespace(),request.getName());
        if(destinationRuleDomain!=null){
            return result.fail("创建失败，已存在");
        }

        if(destinationRuleService.post(request.getNamespace(),request.getName(), template)){
            return result.success("创建成功");
        }else{
            return result.fail("创建失败");
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/fuse/set")
    public JsonResult set(@RequestBody DestinationRuleDomain request){
        JsonResult result=new JsonResult();
        if(request.getOpenFuse()==null){
            return result.fail("未开启");
        }
        if(request.getOpenFuse()==true){
            if(request.getName()==null || request.getName().equals("")){
                return result.fail("名称不能为空");
            }
            if(request.getNamespace()==null || request.getNamespace().equals("")){
                return result.fail("命名空间不能为空");
            }
            if(request.getHttp1MaxPendingRequests()==null){
                return result.fail("http1MaxPendingRequests不能为空");
            }
            if(request.getMaxRequestsPerConnection()==null){
                return result.fail("maxRequestsPerConnection不能为空");
            }
            if(request.getMaxConnections()==null){
                return result.fail("maxConnections不能为空");
            }
            if(request.getMaxEjectionPercent()==null){
                return result.fail("maxEjectionPercent不能为空");
            }
            if(request.getBaseEjectionTime()==null){
                return result.fail("baseEjectionTime不能为空");
            }
            if(request.getConsecutiveErrors()==null){
                return result.fail("consecutiveErrors不能为空");
            }
            if(request.getInterval()==null){
                return result.fail("Interval不能为空");
            }
        }

        if(destinationRuleService.putTrafficPolicy(request.getNamespace(),request.getName(),request)){
            return result.success("设置成功");
        }

        return result.fail("设置失败");
    }
}
