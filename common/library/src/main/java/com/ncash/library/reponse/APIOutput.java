/*
 * ********************************************************************************
 * This file is COPYRIGHT (c) 2023, The Land Administration Corporation Inc (LAC) ALL RIGHTS RESERVED
 * This software is the property of The Land Administration Corporation Inc.
 * It can not be copied, modified, or distributed without the express written permission of
 * The Land Administration Corporation Inc. Contact: info@landadmin.com
 * The information contained in this software is confidential and proprietary.
 * *********************************************************************************
 */

package com.ncash.library.reponse;

import java.io.Serializable;


public class APIOutput<T> implements Serializable {
    private Boolean status;
    private T data;
    private String msg;

    public APIOutput() {
        this.data = (T) new EmptyBean();
        this.msg = "";
    }

    public APIOutput(Boolean status, T data) {
        this.status = status;
        this.data = data;
        this.msg = "";
    }

    public APIOutput(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public APIOutput(Boolean status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "APIOutput{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
