package com.mokn.istio.api.model.k8s.virtualservice;

import com.mokn.istio.api.model.k8s.K8sResultBase;
import com.mokn.istio.api.model.k8s.K8sResultBase_Metadata;

import java.util.List;

public class VirtualServiceList extends K8sResultBase {
    public K8sResultBase_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(K8sResultBase_Metadata metadata) {
        this.metadata = metadata;
    }

    public List<VirtualServiceList_Item> getItems() {
        return items;
    }

    public void setItems(List<VirtualServiceList_Item> items) {
        this.items = items;
    }

    private K8sResultBase_Metadata metadata;
    private List<VirtualServiceList_Item> items;
}
