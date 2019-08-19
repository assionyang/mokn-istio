package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Spec_Http_Route {
    private VirtualService_Spec_Http_Route_Destination destination;

    public VirtualService_Spec_Http_Route_Destination getDestination() {
        return destination;
    }

    public void setDestination(VirtualService_Spec_Http_Route_Destination destination) {
        this.destination = destination;
    }
}
