package com.mokn.istio.api.model.em;

public enum ResourceEnum {

    VIRTUALSERVICE_DEFAULT("VirtualService_Default","虚拟服务-默认路由"),
    VIRTUALSERVICE_CANARY("VirtualService_Canary","虚拟服务-金丝雀路由"),
    VIRTUALSERVICE_WEIGHT("VirtualService_Weight","虚拟服务-流量权重路由");

    private final String value;
    private final String text;

    private ResourceEnum(final String value,final String text){
        this.text=text;
        this.value=value;
    }

    public String getValue(){return this.value;}
    public String getText(){return  this.text;}
}
