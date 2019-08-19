package com.mokn.istio.api.model.k8s.deployment;

public class Deployment_Metadata_Labels {
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

    private String app;
    private String version;
}
