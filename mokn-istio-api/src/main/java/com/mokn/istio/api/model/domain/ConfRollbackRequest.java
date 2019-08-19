package com.mokn.istio.api.model.domain;

public class ConfRollbackRequest {
    private Long sysno;
    private Long recordSysno;

    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public Long getRecordSysno() {
        return recordSysno;
    }

    public void setRecordSysno(Long recordSysno) {
        this.recordSysno = recordSysno;
    }
}
