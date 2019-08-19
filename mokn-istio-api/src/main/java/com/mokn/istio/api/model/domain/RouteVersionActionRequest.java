package com.mokn.istio.api.model.domain;

import com.mokn.istio.api.model.em.VersionStatusEnum;

public class RouteVersionActionRequest {
    private Long sysno;
    private VersionStatusEnum versionStatus;

    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public VersionStatusEnum getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(VersionStatusEnum versionStatus) {
        this.versionStatus = versionStatus;
    }
}
