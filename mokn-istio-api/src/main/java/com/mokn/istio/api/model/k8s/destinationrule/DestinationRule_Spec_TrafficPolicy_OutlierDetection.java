package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_TrafficPolicy_OutlierDetection {
    private String baseEjectionTime;
    private Integer consecutiveErrors;
    private String interval;
    private Integer maxEjectionPercent;

    public String getBaseEjectionTime() {
        return baseEjectionTime;
    }

    public void setBaseEjectionTime(String baseEjectionTime) {
        this.baseEjectionTime = baseEjectionTime;
    }

    public Integer getConsecutiveErrors() {
        return consecutiveErrors;
    }

    public void setConsecutiveErrors(Integer consecutiveErrors) {
        this.consecutiveErrors = consecutiveErrors;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getMaxEjectionPercent() {
        return maxEjectionPercent;
    }

    public void setMaxEjectionPercent(Integer maxEjectionPercent) {
        this.maxEjectionPercent = maxEjectionPercent;
    }
}
