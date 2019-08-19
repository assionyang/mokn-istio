package com.mokn.istio.api.model.k8s.virtualservice;

import java.util.List;

public class VirtualService_Spec {
    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    private List<String> hosts;

    public List<String> getGateways() {
        return gateways;
    }

    public void setGateways(List<String> gateways) {
        this.gateways = gateways;
    }

    private List<String> gateways;

    public List<VirtualService_Spec_Http> getHttp() {
        return http;
    }

    public void setHttp(List<VirtualService_Spec_Http> http) {
        this.http = http;
    }

    private List<VirtualService_Spec_Http> http;
}
