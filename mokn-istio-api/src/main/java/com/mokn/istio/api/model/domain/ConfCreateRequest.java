package com.mokn.istio.api.model.domain;

import java.util.Date;
import java.util.List;

public class ConfCreateRequest {
    private String namespace;
    private String name;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyValueDomain> getItems() {
        return items;
    }

    public void setItems(List<KeyValueDomain> items) {
        this.items = items;
    }

    private List<KeyValueDomain> items;
}
