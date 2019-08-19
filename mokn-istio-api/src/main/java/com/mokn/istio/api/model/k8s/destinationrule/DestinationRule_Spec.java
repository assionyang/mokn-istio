package com.mokn.istio.api.model.k8s.destinationrule;

import java.util.List;

public class DestinationRule_Spec {
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<DestinationRule_Spec_Subset> getSubsets() {
        return subsets;
    }

    public void setSubsets(List<DestinationRule_Spec_Subset> subsets) {
        this.subsets = subsets;
    }

    private String host;
    private List<DestinationRule_Spec_Subset> subsets;

    public DestinationRule_Spec_TrafficPolicy getTrafficPolicy() {
        return trafficPolicy;
    }

    public void setTrafficPolicy(DestinationRule_Spec_TrafficPolicy trafficPolicy) {
        this.trafficPolicy = trafficPolicy;
    }

    private DestinationRule_Spec_TrafficPolicy trafficPolicy;
}
