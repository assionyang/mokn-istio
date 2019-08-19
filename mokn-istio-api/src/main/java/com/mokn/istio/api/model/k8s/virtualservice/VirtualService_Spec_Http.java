package com.mokn.istio.api.model.k8s.virtualservice;

import java.util.List;

public class VirtualService_Spec_Http {
    public List<VirtualService_Spec_Http_Route> getRoute() {
        return route;
    }

    public void setRoute(List<VirtualService_Spec_Http_Route> route) {
        this.route = route;
    }

    public VirtualService_Spec_Http_Fault getFault() {
        return fault;
    }

    public void setFault(VirtualService_Spec_Http_Fault fault) {
        this.fault = fault;
    }

    private List<VirtualService_Spec_Http_Route> route;
    private VirtualService_Spec_Http_Fault fault;
}
