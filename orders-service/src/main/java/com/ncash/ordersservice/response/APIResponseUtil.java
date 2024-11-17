
package com.ncash.ordersservice.response;


import com.ncash.ordersservice.constant.APIConstant;
import com.ncash.ordersservice.constant.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class APIResponseUtil {
    private APIResponseUtil() {
    }

    public static ResponseEntity<Object> getResponseWithData(Object data) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }


    public static ResponseEntity<Object> getResponseWithMessage(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    public static ResponseEntity<Object> getResponseWithErrorMessage(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    public static ResponseEntity<Object> getResponseWithErrorMessageAndErrorCode(String msg) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setMsg(msg);
        apiOutput.setData(null);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        return new ResponseEntity<>(apiOutput, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Object> getResponseForEmptyList() {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(null);
        apiOutput.setMsg(ResponseMessage.AUD_NO_RECORDS_FOUND.toString());
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    public static ResponseEntity<Object> getResponseWithDataAndMessage(Object data, String message) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_OK);
        apiOutput.setMsg(message);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    public static ResponseEntity<Object> getResponseWithDataAndErrorMessage(Object data, String message) {
        APIOutput apiOutput = new APIOutput();
        apiOutput.setData(data);
        apiOutput.setStatus(APIConstant.API_OUTPUT_STATUS_ERROR);
        apiOutput.setMsg(message);
        return new ResponseEntity<>(apiOutput, HttpStatus.OK);
    }

    public static ResponseEntity<Object> getResponseByStatus(APIOutput data) {
        if (data.getStatus()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            return APIResponseUtil.getResponseWithErrorMessageAndErrorCode(data.getMsg());
        }
    }

}

