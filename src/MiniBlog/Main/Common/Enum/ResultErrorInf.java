package MiniBlog.Main.Common.Enum;

import org.springframework.http.HttpStatus;

public enum ResultErrorInf {

    PARAM_IS_EMPTY(100100, "参数缺失"),
    DATA_REQUEST_FAIL(100101, "数据请求失败"),
    USER_UNLOGIN(100102, "用户未登录"),


    ;

    // 错误代码
    private Integer errorCode;
    // 错误信息
    private String errorMessage;
    // 对应的http状态码
    private HttpStatus httpStatus;

    ResultErrorInf(Integer errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    ResultErrorInf(Integer errorCode, String errorMessage) {
        this(errorCode, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
