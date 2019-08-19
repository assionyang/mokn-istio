package com.mokn.istio.api.model.em;

public enum  ConfRecordStatusEnum {
    RUN(1,"待执行"),
    RUN_SUCCESS(2,"执行成功"),
    RUN_FAIL(3,"执行失败"),
    CANCEL(4,"取消");

    private final Integer value;
    private final String text;

    private ConfRecordStatusEnum(final Integer value,final String text){
        this.text=text;
        this.value=value;
    }

    public Integer getValue(){return this.value;}
    public String getText(){return  this.text;}
}
