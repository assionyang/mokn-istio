package com.mokn.istio.api.model.k8s.configmap;

import com.mokn.istio.api.model.k8s.K8sResultBase;

import java.util.Map;

public class ConfigMap extends K8sResultBase {
    private ConfigMap_Metadata metadata;

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

    private Map<String,String> data;
}
