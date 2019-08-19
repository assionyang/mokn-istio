package com.mokn.istio.api.model.k8s.configmap;

import com.mokn.istio.api.model.k8s.K8sResultBase;

import java.util.Map;

public class ConfigMapList_Item extends K8sResultBase {
    private ConfigMap_Metadata metadata;
    private Map<String,String> data;

    public ConfigMap_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ConfigMap_Metadata metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
