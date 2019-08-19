package com.mokn.istio.api.model.k8s.gateway;

import java.util.List;

public class Gateway_Spec_Server {
    private List<String> hosts;
    private Gateway_Spec_Server_Port port;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public Gateway_Spec_Server_Port getPort() {
        return port;
    }

    public void setPort(Gateway_Spec_Server_Port port) {
        this.port = port;
    }
}
