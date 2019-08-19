package com.mokn.istio.api.model.k8s.service;

import com.mokn.istio.api.model.k8s.K8sResultBase;
import com.mokn.istio.api.model.k8s.K8sResultBase_Metadata;

import java.util.List;

public class ServiceList extends K8sResultBase {
    private K8sResultBase_Metadata metadata;
    private List<ServiceList_Item> items;

    public K8sResultBase_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(K8sResultBase_Metadata metadata) {
        this.metadata = metadata;
    }

    public List<ServiceList_Item> getItems() {
        return items;
    }

    public void setItems(List<ServiceList_Item> items) {
        this.items = items;
    }
}
