package com.mokn.istio.api.model.huaweicloud.token;

public class Project {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public Project(){}
    public Project(String id){
        this.id=id;
    }
}
