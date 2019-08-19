package com.mokn.istio.api.model.k8s.pod;

import com.mokn.istio.api.model.k8s.K8sResultBase;
import com.mokn.istio.api.model.k8s.K8sResultBase_Metadata;

import java.util.List;

public class PodList extends K8sResultBase {
    private K8sResultBase_Metadata metadata;
    private List<PodList_Item> items;

    public K8sResultBase_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(K8sResultBase_Metadata metadata) {
        this.metadata = metadata;
    }

    public List<PodList_Item> getItems() {
        return items;
    }

    public void setItems(List<PodList_Item> items) {
        this.items = items;
    }
}
