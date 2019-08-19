package com.mokn.istio.api.model.k8s.namespace;

public class Namespace_Metadata {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    private String name;
    private String selfLink;
    private String uid;
    private String creationTimestamp;
    private Namespace_Metadata_Label labels;

    public Namespace_Metadata_Label getLabels() {
        return labels;
    }

    public void setLabels(Namespace_Metadata_Label labels) {
        this.labels = labels;
    }
}
