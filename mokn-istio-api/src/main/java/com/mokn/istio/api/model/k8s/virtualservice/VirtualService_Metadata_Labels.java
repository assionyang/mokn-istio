package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Metadata_Labels {
    public String getIstioVersionType() {
        return istioVersionType;
    }

    public void setIstioVersionType(String istioVersionType) {
        this.istioVersionType = istioVersionType;
    }

    public String getIstioVersion() {
        return istioVersion;
    }

    public void setIstioVersion(String istioVersion) {
        this.istioVersion = istioVersion;
    }

    private String istioVersionType;
    private String istioVersion;
}
