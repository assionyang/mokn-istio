package com.mokn.istio.api.model.k8s.pod;

import com.mokn.istio.api.model.k8s.K8sResultBase;

public class Pod extends K8sResultBase {
    public Pod_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Pod_Metadata metadata) {
        this.metadata = metadata;
    }

    private Pod_Metadata metadata;

    public Pod_Status getStatus() {
        return status;
    }

    public void setStatus(Pod_Status status) {
        this.status = status;
    }

    private Pod_Status status;
}
