package com.mokn.istio.api.model.em;

public enum VersionStatusEnum {
    CREATED(1,"创建"),
    DEPLOY_ING(2,"部署中"),
    DEPLOY_SUCCESS(3,"部署成功"),
    DEPLOY_FAIL(4,"部署失败"),
    RELEASE_ING(5,"上线中"),
    RELEASE_SUCCESS(6,"上线成功"),
    RELEASE_FAIL(7,"上线失败"),
    ROLLBACK_ING(8,"回滚中"),
    ROLLBACK_SUCCESS(9,"回滚成功"),
    ROLLBACK_FAIL(10,"回滚失败"),
    CANCEL(11,"取消");

    private final Integer value;
    private final String text;

    private VersionStatusEnum(final Integer value,final String text){
        this.text=text;
        this.value=value;
    }

    public Integer getValue(){return this.value;}
    public String getText(){return  this.text;}
}
