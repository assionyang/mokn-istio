package com.mokn.istio.api.model.domain;

import java.util.List;

public class ConfUpdateRequest {
    private Long sysno;
    private List<KeyValueDomain> items;

    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public List<KeyValueDomain> getItems() {
        return items;
    }

    public void setItems(List<KeyValueDomain> items) {
        this.items = items;
    }
}
