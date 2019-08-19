package com.mokn.istio.api.model.huaweicloud.token;

import java.util.ArrayList;
import java.util.List;

public class Identity {
    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    private List<String> methods;
    private Password password;

    public Identity(){}
    public Identity(String method,Password password){
        this.methods=new ArrayList<>();
        this.methods.add(method);
        this.password=password;
    }
}
