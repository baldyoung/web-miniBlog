package MiniBlog.Main.Common.response;

import MiniBlog.Main.Common.Enum.ResultErrorInf;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = -3809382578233943939L;
    public static final String RESULT_SUCCESS = "success",
        RESULT_FAIL = "fail";
    // 本次请求的结果
    String result;
    // 对本次请求结果的一个备注信息，一般用来记录请求失败的原因
    String inf;
    // 本次请求所返回的数据
    Object data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getInf() {
        return inf;
    }

    public Object getData() {
        return data;
    }

    public Result(String result, String inf, Object data) {
        this.result = result;
        this.inf = inf;
        this.data = data;
    }

    public Result(ResultErrorInf error) {
        this(RESULT_FAIL, error.getErrorMessage(), null);
    }

    public Result() {
        this.result = RESULT_SUCCESS;
        this.inf = "";
        this.data = null;
    }

    public static Result success(Object data) {
        return new Result(RESULT_SUCCESS, "", data);
    }

    public static Result success() {
        return new Result(RESULT_SUCCESS, "", null);
    }

    public static Result fail(String inf) {
        return new Result(RESULT_FAIL, inf, null);
    }

    public static Result fail(ResultErrorInf error) {
        return new Result(error);
    }

    public static Result fail() {
        return new Result(RESULT_FAIL, null, null);
    }
}
