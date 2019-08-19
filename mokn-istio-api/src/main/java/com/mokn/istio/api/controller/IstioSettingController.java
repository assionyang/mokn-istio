package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.IstionSettingDomain;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.service.k8s.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/istio/setting")
public class IstioSettingController {

    @Autowired
    private ServiceService serviceService;

    @LoginPassport
    @GetMapping(value = "/load")
    public JsonResult<IstionSettingDomain> load(){
        JsonResult<IstionSettingDomain> result=new JsonResult<>();
        IstionSettingDomain setting=new IstionSettingDomain();
        setting.setIngreeGatewaySpecType(serviceService.getService("istio-system","istio-ingressgateway").getSpec().getType());
        return result.success(setting);
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/set")
    public JsonResult setIstioIngressGatewayType(@RequestBody IstionSettingDomain request){
        JsonResult result=new JsonResult();
        if(request.getSettingType().equals("istio-ingressgateway-type")){
            if(!request.getIngreeGatewaySpecType().equals("LoadBalancer") && !request.getIngreeGatewaySpecType().equals("NodePort")){
                return result.fail("无效的设置");
            }
            serviceService.patch("istio-system","istio-ingressgateway","{\"spec\":{\"type\":\""+request.getIngreeGatewaySpecType()+"\"}}");
            return result.success("设置成功");
        }
        return result.fail("设置失败");
    }
}
