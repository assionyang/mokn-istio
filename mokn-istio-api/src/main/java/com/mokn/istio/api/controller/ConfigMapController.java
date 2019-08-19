package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.JwtUtil;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.ConfigMapDomain;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceAndNameAndAppRequest;
import com.mokn.istio.api.service.k8s.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/configmap")
public class ConfigMapController {

    @Autowired
    private ConfigMapService configMapService;
    @Autowired
    private JwtUtil jwtUtil;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<ConfigMapDomain> list(@RequestParam(value = "namespace",required = false) String namespace,
                                            @RequestHeader(value = "Authorization",required = false) String auth){
        JsonResult<ConfigMapDomain> result=new JsonResult<>();
        namespace=namespace==null?"default":namespace;
        namespace=namespace==""?"default":namespace;
        List<ConfigMapDomain> domains=configMapService.listDomain(namespace);
            for(int i=0;i<domains.size();i++){
                List<String> dataList=new ArrayList<>();
                for(Map.Entry<String,String> entry:domains.get(i).getData().entrySet()){
                    if(auth==null || auth.equals("") || !jwtUtil.getValueFromToken(auth,"role").equals("admin")){
                        domains.get(i).getData().put(entry.getKey(),"******");
                    }
                    dataList.add(entry.getKey()+":"+entry.getValue());
                }
                domains.get(i).setDataList(dataList);
            }
        return result.success(domains);
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(configMapService.delete(request.getNamespace(),request.getName())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }
}
