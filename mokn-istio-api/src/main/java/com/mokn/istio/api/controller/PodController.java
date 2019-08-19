package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceAndNameAndAppRequest;
import com.mokn.istio.api.model.domain.PodDomain;
import com.mokn.istio.api.service.k8s.PodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pod")
public class PodController {

    @Autowired
    private PodService podService;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<PodDomain> list(@RequestParam(value = "namespace",required = false) String namespace){
        JsonResult<PodDomain> result=new JsonResult<>();
        if(namespace==null || namespace.equals("")){
            return result.success(podService.listDomain());
        }else{
            return result.success(podService.listDomain(namespace));
        }
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(podService.delete(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }
}
