package com.mokn.istio.api.model.domain;

import java.util.List;

public class JsonResult<T> {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getRow() {
        return row;
    }

    public void setRow(T row) {
        this.row = row;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isSuccess(){
        if(code.equals("200")){
            return true;
        }else{
            return false;
        }
    }

    public JsonResult<T> success(){
        this.code="200";
        this.message="success";
        return this;
    }
    public JsonResult<T> success(String message){
        this.code="200";
        this.message=message;
        return this;
    }
    public JsonResult<T> success(T row){
        this.code="200";
        this.message="success";
        this.row=row;
        return this;
    }
    public JsonResult<T> success(List<T> rows){
        this.code="200";
        this.message="success";
        this.rows=rows;
        return this;
    }
    public JsonResult<T> success(List<T> rows,long total){
        this.code="200";
        this.message="success";
        this.rows=rows;
        this.total=total;
        return this;
    }
    public JsonResult<T> success(String message,T row){
        this.code="200";
        this.message=message;
        this.row=row;
        return this;
    }
    public JsonResult<T> success(String message,List<T> rows){
        this.code="200";
        this.message=message;
        this.rows=rows;
        return this;
    }
    public JsonResult<T> success(String message,List<T> rows,long total){
        this.code="200";
        this.message=message;
        this.rows=rows;
        this.total=total;
        return this;
    }

    public JsonResult<T> fail(String message){
        this.code="500";
        this.message=message;
        return this;
    }

    private String code;
    private String message;
    private boolean isSuccess;
    private T row;
    private List<T> rows;
    private long total;
}
