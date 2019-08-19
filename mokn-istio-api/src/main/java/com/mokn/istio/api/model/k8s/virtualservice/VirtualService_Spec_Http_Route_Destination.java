package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Spec_Http_Route_Destination {
    private String host;
    private String subset;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSubset() {
        return subset;
    }

    public void setSubset(String subset) {
        this.subset = subset;
    }
}
