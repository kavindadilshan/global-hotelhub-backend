package com.bolton.globalhotelhub.dto.response;

public class ErrorMessageResponseDTO {

    private boolean success;
    private int errorCode;
    private Object errorContent;

    public ErrorMessageResponseDTO() {
    }

    public ErrorMessageResponseDTO(boolean success, int errorCode, Object errorContent) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorContent = errorContent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(Object errorContent) {
        this.errorContent = errorContent;
    }

    @Override
    public String toString() {
        return "ErrorMessageResponseDTO{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", errorContent=" + errorContent +
                '}';
    }
}
