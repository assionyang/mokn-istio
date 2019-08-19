package com.mokn.istio.api.model.k8s.gateway;

import java.util.List;

public class Gateway_Spec {
    private Gateway_Spec_Selector selector;
    private List<Gateway_Spec_Server> servers;

    public Gateway_Spec_Selector getSelector() {
        return selector;
    }

    public void setSelector(Gateway_Spec_Selector selector) {
        this.selector = selector;
    }

    public List<Gateway_Spec_Server> getServers() {
        return servers;
    }

    public void setServers(List<Gateway_Spec_Server> servers) {
        this.servers = servers;
    }
}
