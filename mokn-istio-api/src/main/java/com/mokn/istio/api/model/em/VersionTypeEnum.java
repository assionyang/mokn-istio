package com.mokn.istio.api.model.em;

public enum  VersionTypeEnum {
    RELEASE(1,"直接发布"),
    WEIGHT(2,"流量权重"),
    CANARY(3,"金丝雀");

    private final Integer value;
    private final String text;

    private VersionTypeEnum(final Integer value,final String text){
        this.text=text;
        this.value=value;
    }

    public Integer getValue(){return this.value;}
    public String getText(){return  this.text;}
}
