package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.DeploymentDomain;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceAndNameAndAppRequest;
import com.mokn.istio.api.model.domain.VirtualServiceDomain;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import com.mokn.istio.api.service.k8s.DeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/deployment")
public class DeploymentController {

    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private VirtualServiceService virtualServiceService;


    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<DeploymentDomain> listDeploymentDomain(@RequestParam(value = "namespace",required = false) String namepspace){
        JsonResult<DeploymentDomain> result=new JsonResult<>();
        if(namepspace==null || namepspace.equals("")){
            return result.success(deploymentService.listDeploymentDomain());
        }else{
            return result.success(deploymentService.listDeploymentDomain(namepspace));
        }
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult deleteDeployment(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(request.getNamespace().equals("kube-system") || request.getNamespace().equals("istio-system") || request.getNamespace().equals("kube-public")){
            return result.fail("系统命名空间下的资源不能删除");
        }
        VirtualServiceDomain virtualService=virtualServiceService.getVirtualServiceDomain(request.getNamespace(),request.getApp());
        if(virtualService!=null){
            String deploymentName=virtualService.getName()+"-"+virtualService.getVersion();
            if(deploymentName.equals(request.getName())){
                return result.fail("绑定VirtualService的资源不能删除");
            }
        }
        if(deploymentService.deleteDeployment(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }
}
