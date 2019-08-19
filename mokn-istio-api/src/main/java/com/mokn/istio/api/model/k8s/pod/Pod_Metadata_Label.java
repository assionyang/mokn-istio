package com.mokn.istio.api.model.k8s.pod;

public class Pod_Metadata_Label {
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
