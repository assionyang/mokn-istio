package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_TrafficPolicy {
    private DestinationRule_Spec_TrafficPolicy_ConnectionPoll connectionPoll;
    private DestinationRule_Spec_TrafficPolicy_OutlierDetection outlierDetection;

    public DestinationRule_Spec_TrafficPolicy_ConnectionPoll getConnectionPoll() {
        return connectionPoll;
    }

    public void setConnectionPoll(DestinationRule_Spec_TrafficPolicy_ConnectionPoll connectionPoll) {
        this.connectionPoll = connectionPoll;
    }

    public DestinationRule_Spec_TrafficPolicy_OutlierDetection getOutlierDetection() {
        return outlierDetection;
    }

    public void setOutlierDetection(DestinationRule_Spec_TrafficPolicy_OutlierDetection outlierDetection) {
        this.outlierDetection = outlierDetection;
    }
}
