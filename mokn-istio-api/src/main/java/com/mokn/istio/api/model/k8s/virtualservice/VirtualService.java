package com.mokn.istio.api.model.k8s.virtualservice;

import com.mokn.istio.api.model.k8s.K8sResultBase;

public class VirtualService extends K8sResultBase {
    public VirtualService_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(VirtualService_Metadata metadata) {
        this.metadata = metadata;
    }

    private VirtualService_Metadata metadata;
    private VirtualService_Spec spec;

    public VirtualService_Spec getSpec() {
        return spec;
    }

    public void setSpec(VirtualService_Spec spec) {
        this.spec = spec;
    }
}
