package com.mokn.istio.api.model.domain;

import com.mokn.istio.api.model.em.VersionStatusEnum;
import com.mokn.istio.api.model.em.VersionTypeEnum;

public class RouteVersionItemDomain {
    public VersionTypeEnum getVersionType() {
        return versionType;
    }

    public void setVersionType(VersionTypeEnum versionType) {
        this.versionType = versionType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNameHost() {
        return nameHost;
    }

    public void setNameHost(String nameHost) {
        this.nameHost = nameHost;
    }

    public String getSubsetNew() {
        return subsetNew;
    }

    public void setSubsetNew(String subsetNew) {
        this.subsetNew = subsetNew;
    }

    private VersionTypeEnum versionType;
    private String namespace;
    private String nameHost;
    private String subsetNew;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getHttpKey() {
        return httpKey;
    }

    public void setHttpKey(String httpKey) {
        this.httpKey = httpKey;
    }

    public String getHttpValue() {
        return httpValue;
    }

    public void setHttpValue(String httpValue) {
        this.httpValue = httpValue;
    }

    private Integer weight;
    private String httpKey;
    private String httpValue;
}
