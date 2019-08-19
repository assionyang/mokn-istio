package com.mokn.istio.api.model.huaweicloud.token;

public class Body {
    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    private Auth auth;

    public Body(){}
    public Body(Auth auth){
        this.auth=auth;
    }
}
