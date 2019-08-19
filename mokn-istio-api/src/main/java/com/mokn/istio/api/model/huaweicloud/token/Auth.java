package com.mokn.istio.api.model.huaweicloud.token;

public class Auth {
    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    private Identity identity;
    private Scope scope;

    public Auth(){}
    public Auth(Identity identity,Scope scope){
        this.identity=identity;
        this.scope=scope;
    }
}
