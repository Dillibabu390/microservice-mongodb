
package com.ncash.productservice.response;

import com.ncash.productservice.constant.APIConstant;
import com.ncash.productservice.constant.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * The type Api response util.
 */
public class APIResponseUtil {
    private APIResponseUtil() {
    }

    /**
     * Gets response with data.
     *
     * @param data the data
     * @return the response with data
     */
    public static ResponseEntity<Object> getResponseWithData(Object data) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }


    /**
     * Gets response with message.
     *
     * @param msg the msg
     * @return the response with message
     */
    public static ResponseEntity<Object> getResponseWithMessage(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    /**
     * Gets response with error message.
     *
     * @param msg the msg
     * @return the response with error message
     */
    public static ResponseEntity<Object> getResponseWithErrorMessage(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    /**
     * Gets response with error message and error code.
     *
     * @param msg the msg
     * @return the response with error message and error code
     */
    public static ResponseEntity<Object> getResponseWithErrorMessageAndErrorCode(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        return new ResponseEntity<>(apiOutput, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets response for empty list.
     *
     * @return the response for empty list
     */
    public static ResponseEntity<Object> getResponseForEmptyList() {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(null);
        apiOutput.setMsg(ResponseMessage.AUD_NO_RECORDS_FOUND.toString());
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    /**
     * Gets response with data and message.
     *
     * @param data    the data
     * @param message the message
     * @return the response with data and message
     */
    public static ResponseEntity<Object> getResponseWithDataAndMessage(Object data, String message) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        apiOutput.setMsg(message);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    /**
     * Gets response with data and error message.
     *
     * @param data    the data
     * @param message the message
     * @return the response with data and error message
     */
    public static ResponseEntity<Object> getResponseWithDataAndErrorMessage(Object data, String message) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        apiOutput.setMsg(message);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    /**
     * Gets response by status.
     *
     * @param data the data
     * @return the response by status
     */
    public static ResponseEntity<Object> getResponseByStatus(APIOutput data) {
        if (data.getStatus()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return APIResponseUtil.getResponseWithErrorMessageAndErrorCode(data.getMsg());
        }
    }

}

