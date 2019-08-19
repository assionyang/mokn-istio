package com.mokn.istio.api.model.huaweicloud.token;

public class Password {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public Password(){}
    public Password(User user){
        this.user=user;
    }
}
