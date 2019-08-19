package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Spec_Http_Fault_Delay {
    private String fixedDelay;
    private Integer percent;

    public String getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(String fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
