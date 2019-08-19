package com.mokn.istio.api.model.k8s.namespace;

import com.mokn.istio.api.model.k8s.K8sResultBase;

public class Namespace extends K8sResultBase {
    public Namespace_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Namespace_Metadata metadata) {
        this.metadata = metadata;
    }

    public Namespace_Status getStatus() {
        return status;
    }

    public void setStatus(Namespace_Status status) {
        this.status = status;
    }

    private Namespace_Metadata metadata;
    private Namespace_Status status;
}
