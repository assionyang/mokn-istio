package com.mokn.istio.api.controller;

import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.domain.GatewayDomain;
import com.mokn.istio.api.model.domain.NamespaceDomain;
import com.mokn.istio.api.model.domain.ServiceDomain;
import com.mokn.istio.api.model.domain.VirtualServiceDomain;
import com.mokn.istio.api.service.k8s.GatewayService;
import com.mokn.istio.api.service.k8s.ServiceService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import com.mokn.istio.api.service.k8s.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/namespaces")
public class NamespaceController {

    @Autowired
    private NamespaceService namespaceService;
    @Autowired
    private VirtualServiceService virtualServiceService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private GatewayService gatewayService;

    @LoginPassport
    @GetMapping(value = "/all")
    public List<NamespaceDomain> namespaces(){
        List<NamespaceDomain> namespaces = namespaceService.listNamespaceDomain();
        for(int i=0;i<namespaces.size();i++){
            List<VirtualServiceDomain> virtualServices=virtualServiceService.listVirtualServiceDomain(namespaces.get(i).getName());
            if(virtualServices!=null){
                namespaces.get(i).setVirtualServices(virtualServices);
            }
            List<ServiceDomain> services=serviceService.listServiceDomain(namespaces.get(i).getName());
            if(services!=null){
                namespaces.get(i).setServices(services);
            }
//            List<GatewayDomain> gateways=gatewayService.listDomain(namespaces.get(i).getName());
//            if(gateways!=null){
//                namespaces.get(i).setGateways(gateways);
//            }
        }
        return namespaces;
    }
}
