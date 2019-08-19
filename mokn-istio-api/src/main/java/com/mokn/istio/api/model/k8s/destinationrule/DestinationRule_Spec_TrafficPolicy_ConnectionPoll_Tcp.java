package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp {
    private Integer maxConnections;

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }
}
