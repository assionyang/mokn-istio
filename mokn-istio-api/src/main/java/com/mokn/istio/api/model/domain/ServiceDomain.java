package com.mokn.istio.api.model.domain;

public class ServiceDomain {
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

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    private String name;
    private String namespace;
    private String uid;
    private String resourceVersion;
    private String creationTimestamp;
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public ServiceDomain(){}
    public ServiceDomain(String name,String namespace,String uid,String resourceVersion,String creationTimestamp,String app){
        this.name=name;
        this.namespace=namespace;
        this.uid=uid;
        this.resourceVersion=resourceVersion;
        this.creationTimestamp=creationTimestamp;
        this.app=app;
    }
}
