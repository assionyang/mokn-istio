package com.mokn.istio.api.jobs.batch;

import com.mokn.istio.api.common.Constant;
import com.mokn.istio.api.model.domain.DashboardData;
import com.mokn.istio.api.model.domain.NamespaceDomain;
import com.mokn.istio.api.service.k8s.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DashboardJob {

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

    @Scheduled(cron = "0/59 * * * * ?")
    public void run(){

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

        Constant.DASHBOARD_DATA=data;
        Constant.DASHBOARD_DATA_TIME=new Date();
    }
}
