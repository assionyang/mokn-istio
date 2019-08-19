package com.mokn.istio.api.model.k8s.deployment;

import com.mokn.istio.api.model.k8s.K8sResultBase;
import com.mokn.istio.api.model.k8s.K8sResultBase_Metadata;

import java.util.List;

public class DeploymentList extends K8sResultBase {
    public K8sResultBase_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(K8sResultBase_Metadata metadata) {
        this.metadata = metadata;
    }

    public List<DeploymentList_Item> getItems() {
        return items;
    }

    public void setItems(List<DeploymentList_Item> items) {
        this.items = items;
    }

    private K8sResultBase_Metadata metadata;
    private List<DeploymentList_Item> items;
}
