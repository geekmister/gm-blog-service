package cn.gk.gmblog.adm.config;

/**
 * @Description:
 * 外层响应枚举，OREnum为缩写名称，全名是OuterResponseEnum，用于接口返回数据的外层封装。
 * 枚举值说明：
 * @value SUCCESS，代表请求接口成功且没有全局错误或者异常
 * @value FAILED，
 *        代表请求接口发生全局性错误，api定义此类错误是需要开发跟进的错误，比如调用第三方接口返回错误时将不对前端进行错误告知只告知发生了错误
 * @value EXCEPTION，代表请求接口发生全局性异常，api定义此类异常是程序发生了报错，此类异常是需要开发跟进的异常，比如空指针异常
 * @value INVALID_TOKEN，代表用户发起的请求没有访问凭证或者访问凭证错误，api定义此类访问为非法访问
 * @Author: Mr.geek
 * @Date: 2021/12/8.
 */
public enum OREnum {
    SUCCESS("SUCCESS", "响应成功！"),
    FAILED("FAILED", "服务器发生错误，请稍后重试！"),
    EXCEPTION("EXCEPTION", "服务器发生异常，请稍后重试！"),
    INVALID_TOKEN("INVALID_TOKEN", "非法访问！");

    private String status;
    private String message;

    private OREnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
