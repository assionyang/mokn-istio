package com.mokn.istio.api.model.k8s;

public class K8sResultBase_Metadata {
    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    private String resourceVersion;
    private String selfLink;
}
