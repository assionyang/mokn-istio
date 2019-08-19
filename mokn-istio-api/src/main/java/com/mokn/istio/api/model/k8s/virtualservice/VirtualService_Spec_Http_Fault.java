package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Spec_Http_Fault {
    private VirtualService_Spec_Http_Fault_Delay delay;
    private VirtualService_Spec_Http_Fault_Abort abort;

    public VirtualService_Spec_Http_Fault_Delay getDelay() {
        return delay;
    }

    public void setDelay(VirtualService_Spec_Http_Fault_Delay delay) {
        this.delay = delay;
    }

    public VirtualService_Spec_Http_Fault_Abort getAbort() {
        return abort;
    }

    public void setAbort(VirtualService_Spec_Http_Fault_Abort abort) {
        this.abort = abort;
    }
}
