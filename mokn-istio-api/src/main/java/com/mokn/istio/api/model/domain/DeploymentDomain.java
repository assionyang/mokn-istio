package com.mokn.istio.api.model.domain;

public class DeploymentDomain {
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

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String name;
    private String namespace;
    private String uid;
    private String resourceVersion;
    private String app;
    private String version;

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public Integer getAvailableReplicas() {
        return availableReplicas;
    }

    public void setAvailableReplicas(Integer availableReplicas) {
        this.availableReplicas = availableReplicas;
    }

    private Integer replicas;
    private Integer availableReplicas;

    public DeploymentDomain(){}
    public DeploymentDomain(String name,String namespace,String uid,String resourceVersion,String app,String version){
        this.name=name;
        this.namespace=namespace;
        this.uid=uid;
        this.resourceVersion=resourceVersion;
        this.app=app;
        this.version=version;
    }

    private String creationTimestamp;

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
