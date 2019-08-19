package com.mokn.istio.api.model.k8s.gateway;

import com.mokn.istio.api.model.k8s.K8sResultBase;

public class GatewayList_Item extends K8sResultBase {
    private Gateway_Metadata metadata;
    private Gateway_Spec spec;

    public Gateway_Spec getSpec() {
        return spec;
    }

    public void setSpec(Gateway_Spec spec) {
        this.spec = spec;
    }

    public Gateway_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Gateway_Metadata metadata) {
        this.metadata = metadata;
    }
}
