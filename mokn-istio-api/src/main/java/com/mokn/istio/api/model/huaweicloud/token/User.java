package com.mokn.istio.api.model.huaweicloud.token;

public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    private String name;
    private String password;
    private Domain domain;

    public User(){}
    public User(String name,String password,Domain domain){
        this.name=name;
        this.password=password;
        this.domain=domain;
    }
}
