package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.Constant;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.DashboardData;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceDomain;
import com.mokn.istio.api.service.k8s.GatewayService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import com.mokn.istio.api.service.k8s.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @Autowired
    private NamespaceService namespaceService;
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private PodService podService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ConfigMapService configMapService;
    @Autowired
    private VirtualServiceService virtualServiceService;
    @Autowired
    private GatewayService gatewayService;

    @LoginPassport
    @GetMapping(value = "/data")
    public JsonResult<DashboardData> data(){
        JsonResult<DashboardData> result=new JsonResult<>();

        if(Constant.DASHBOARD_DATA!=null && new Date().getTime()-Constant.DASHBOARD_DATA_TIME.getTime()<86400000L){
            return result.success(Constant.DASHBOARD_DATA);
        }

        DashboardData data=new DashboardData();
        data.setNamespaceQty(namespaceService.listNamespaceDomain()==null?0:namespaceService.listNamespaceDomain().size());
        data.setDeploymentQty(deploymentService.listDeploymentDomain()==null?0:deploymentService.listDeploymentDomain().size());
        data.setServiceQty(serviceService.listServiceDomain()==null?0:serviceService.listServiceDomain().size());
        int configMapQty=0;
        int virtualServiceQty=0;
        int gatewayQty=0;
        int podQty=0;
        for(NamespaceDomain namespace:namespaceService.listNamespaceDomain()){
            configMapQty+=configMapService.listDomain(namespace.getName())==null?0:configMapService.listDomain(namespace.getName()).size();
            virtualServiceQty+=virtualServiceService.listVirtualServiceDomain(namespace.getName())==null?0:virtualServiceService.listVirtualServiceDomain(namespace.getName()).size();
            gatewayQty+=gatewayService.listDomain(namespace.getName())==null?0:gatewayService.listDomain(namespace.getName()).size();
            podQty+=podService.listDomain(namespace.getName())==null?0:podService.listDomain(namespace.getName()).size();
        }
        data.setConfigMapQty(configMapQty);
        data.setVeritualServiceQty(virtualServiceQty);
        data.setGatewayQty(gatewayQty);
        data.setPodQty(podQty);
        data.setSecretQty(0);

        return result.success(data);
    }
}
