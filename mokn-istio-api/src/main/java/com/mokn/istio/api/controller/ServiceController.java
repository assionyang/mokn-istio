package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.*;
import com.mokn.istio.api.service.k8s.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;


    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<ServiceDomain> list(@RequestParam(value = "namespace",required = false) String namepspace){
        JsonResult<ServiceDomain> result=new JsonResult<>();
        if(namepspace==null || namepspace.equals("")){
            return result.success(serviceService.listServiceDomain());
        }else{
            return result.success(serviceService.listServiceDomain(namepspace));
        }
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(request.getNamespace().equals("kube-system") || request.getNamespace().equals("istio-system") || request.getNamespace().equals("kube-public")){
            return result.fail("系统命名空间下的资源不能删除");
        }
        if(serviceService.deleteService(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }
}
