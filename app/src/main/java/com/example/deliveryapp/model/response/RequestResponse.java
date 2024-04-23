package com.example.deliveryapp.model.response;

public class RequestResponse {
    private String message;
    private String status;
    private int code;

    public RequestResponse() {
    }

    public RequestResponse(String message, String status, int code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", code=" + code +
                '}';
    }
}
