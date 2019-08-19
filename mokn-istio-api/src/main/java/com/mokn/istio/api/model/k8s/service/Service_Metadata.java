package com.mokn.istio.api.model.k8s.service;

public class Service_Metadata {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    private String name;
    private String namespace;
    private String selfLink;
    private String uid;
    private String resourceVersion;
    private String creationTimestamp;

    public Service_Metadata_Labels getLabels() {
        return labels;
    }

    public void setLabels(Service_Metadata_Labels labels) {
        this.labels = labels;
    }

    private Service_Metadata_Labels labels;
}
