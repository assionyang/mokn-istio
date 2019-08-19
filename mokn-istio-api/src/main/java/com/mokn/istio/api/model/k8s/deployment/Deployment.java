package com.mokn.istio.api.model.k8s.deployment;

import com.mokn.istio.api.model.k8s.K8sResultBase;

public class Deployment extends K8sResultBase {
    public Deployment_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Deployment_Metadata metadata) {
        this.metadata = metadata;
    }

    private Deployment_Metadata metadata;
    private Deployment_Status status;

    public Deployment_Status getStatus() {
        return status;
    }

    public void setStatus(Deployment_Status status) {
        this.status = status;
    }
}
