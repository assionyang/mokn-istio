package com.mokn.istio.api.model.k8s.virtualservice;

public class VirtualService_Spec_Http_Fault_Abort {
    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    private Integer httpStatus;
    private Integer percent;
}
