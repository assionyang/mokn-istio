package com.mokn.istio.api.model.k8s.pod;

public class PodList_Item {
    public Pod_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Pod_Metadata metadata) {
        this.metadata = metadata;
    }

    private Pod_Metadata metadata;
    private Pod_Status status;

    public Pod_Status getStatus() {
        return status;
    }

    public void setStatus(Pod_Status status) {
        this.status = status;
    }
}
