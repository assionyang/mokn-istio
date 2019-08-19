package com.mokn.istio.api.model.k8s.namespace;

public class Namespace_Status {
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    private String phase;
}
