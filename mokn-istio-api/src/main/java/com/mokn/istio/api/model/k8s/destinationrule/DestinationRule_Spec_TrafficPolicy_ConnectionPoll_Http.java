package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http {
    private Integer http1MaxPendingRequests;
    private Integer maxRequestsPerConnection;

    public Integer getHttp1MaxPendingRequests() {
        return http1MaxPendingRequests;
    }

    public void setHttp1MaxPendingRequests(Integer http1MaxPendingRequests) {
        this.http1MaxPendingRequests = http1MaxPendingRequests;
    }

    public Integer getMaxRequestsPerConnection() {
        return maxRequestsPerConnection;
    }

    public void setMaxRequestsPerConnection(Integer maxRequestsPerConnection) {
        this.maxRequestsPerConnection = maxRequestsPerConnection;
    }
}
