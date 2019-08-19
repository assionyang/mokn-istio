package com.mokn.istio.api.model.k8s.namespace;

import com.google.gson.annotations.SerializedName;

public class Namespace_Metadata_Label {
    public String getIstioInjection() {
        return istioInjection;
    }

    public void setIstioInjection(String istioInjection) {
        this.istioInjection = istioInjection;
    }

    @SerializedName("istio-injection")
    private String istioInjection;
}
