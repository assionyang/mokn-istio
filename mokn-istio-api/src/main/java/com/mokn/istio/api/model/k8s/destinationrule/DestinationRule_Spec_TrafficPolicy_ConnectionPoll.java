package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_TrafficPolicy_ConnectionPoll {
    private DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http http;
    private DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp tcp;

    public DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http getHttp() {
        return http;
    }

    public void setHttp(DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http http) {
        this.http = http;
    }

    public DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp getTcp() {
        return tcp;
    }

    public void setTcp(DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp tcp) {
        this.tcp = tcp;
    }
}
