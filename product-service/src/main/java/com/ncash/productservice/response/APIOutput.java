/*
 * ********************************************************************************
 * This file is COPYRIGHT (c) 2023, The Land Administration Corporation Inc (LAC) ALL RIGHTS RESERVED
 * This software is the property of The Land Administration Corporation Inc.
 * It can not be copied, modified, or distributed without the express written permission of
 * The Land Administration Corporation Inc. Contact: info@landadmin.com
 * The information contained in this software is confidential and proprietary.
 * *********************************************************************************
 */

package com.ncash.productservice.response;

import java.io.Serializable;


/**
 * The type Api output.
 *
 * @param <T> the type parameter
 */
public class APIOutput<T> implements Serializable {
    private Boolean status;
    private T data;
    private String msg;

    /**
     * Instantiates a new Api output.
     */
    public APIOutput() {
        this.data = (T) new EmptyBean();
        this.msg = "";
    }

    /**
     * Instantiates a new Api output.
     *
     * @param status the status
     * @param data   the data
     */
    public APIOutput(Boolean status, T data) {
        this.status = status;
        this.data = data;
        this.msg = "";
    }

    /**
     * Instantiates a new Api output.
     *
     * @param status the status
     * @param msg    the msg
     */
    public APIOutput(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * Instantiates a new Api output.
     *
     * @param status the status
     * @param data   the data
     * @param msg    the msg
     */
    public APIOutput(Boolean status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
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
