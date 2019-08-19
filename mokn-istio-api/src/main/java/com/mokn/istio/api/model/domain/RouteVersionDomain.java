package com.mokn.istio.api.model.domain;

import com.mokn.istio.api.model.em.VersionTypeEnum;

import java.util.List;

public class RouteVersionDomain {
    public VersionTypeEnum getVersionType() {
        return versionType;
    }

    public void setVersionType(VersionTypeEnum versionType) {
        this.versionType = versionType;
    }

    public String getVersionTitle() {
        return versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    public String getVersionRemark() {
        return versionRemark;
    }

    public void setVersionRemark(String versionRemark) {
        this.versionRemark = versionRemark;
    }

    public List<RouteVersionItemDomain> getItems() {
        return items;
    }

    public void setItems(List<RouteVersionItemDomain> items) {
        this.items = items;
    }

    private VersionTypeEnum versionType;
    private String versionTitle;
    private String versionRemark;
    private List<RouteVersionItemDomain> items;
}
