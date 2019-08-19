package com.mokn.istio.api.model.k8s.pod;

public class Pod_Status {
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    private String phase;
}
