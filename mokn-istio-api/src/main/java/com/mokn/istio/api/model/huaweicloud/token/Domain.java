package com.mokn.istio.api.model.huaweicloud.token;

public class Domain {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Domain(){}
    public Domain(String name){
        this.name=name;
    }
}
