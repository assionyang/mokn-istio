package com.mokn.istio.api.model.domain;

import com.mokn.istio.api.model.k8s.gateway.Gateway;

import java.util.List;

public class NamespaceDomain {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String name;
    private String uid;

    public List<VirtualServiceDomain> getVirtualServices() {
        return virtualServices;
    }

    public void setVirtualServices(List<VirtualServiceDomain> virtualServices) {
        this.virtualServices = virtualServices;
    }

    private List<VirtualServiceDomain> virtualServices;
    private List<ServiceDomain> services;

    public List<GatewayDomain> getGateways() {
        return gateways;
    }

    public void setGateways(List<GatewayDomain> gateways) {
        this.gateways = gateways;
    }

    private List<GatewayDomain> gateways;

    public List<ServiceDomain> getServices() {
        return services;
    }

    public void setServices(List<ServiceDomain> services) {
        this.services = services;
    }

    public NamespaceDomain(){}
    public NamespaceDomain(String name,String uid){
        this.name=name;
        this.uid=uid;
    }
    public NamespaceDomain(String name,String uid,List<VirtualServiceDomain> virtualServices){
        this.name=name;
        this.uid=uid;
        this.virtualServices=virtualServices;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getIstioInjection() {
        return istioInjection;
    }

    public void setIstioInjection(String istioInjection) {
        this.istioInjection = istioInjection;
    }

    private String creationTimestamp;
    private String istioInjection;
}
