package com.mokn.istio.api.model.k8s.service;

public class ServiceList_Item {
    private Service_Metadata metadata;
    private Service_Spec spec;

    public Service_Spec getSpec() {
        return spec;
    }

    public void setSpec(Service_Spec spec) {
        this.spec = spec;
    }

    public Service_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Service_Metadata metadata) {
        this.metadata = metadata;
    }
}
