package com.mokn.istio.api.model.huaweicloud.token;

public class Scope {
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    Project project;

    public Scope(){}
    public Scope(Project project){
        this.project=project;
    }
}
