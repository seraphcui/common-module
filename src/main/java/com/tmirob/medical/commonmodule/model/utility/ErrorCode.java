package com.tmirob.medical.commonmodule.model.utility;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/29
 */
public enum ErrorCode {
    Success(0, "成功"),
    Fail(-1, "失败"),

    //Common
    InvalidParameter(10001, "参数不正确。"),
    InvalidDateTime(10002, "不正确的时间格式。"),
    NullObject(10003, "字段不能为空。"),
    ValidationFail(10004, "数据验证失败。"),
    InvalidMessage(10005, "消息格式不正确。"),

    //person
    PersonExist(20001, "该用户名已存在！"),
    NoPerson(20002, "该用户不存在"),
    EditDenied(20003, "很抱歉，您没有权限修改该字段。"),
    WrongOldPassword(20004, "旧密码验证错误！"),
    LoginFail(20005, "登录失败，请检查密码是否正确！"),
    NoFingerPrint(20006, "该指纹不存在。"),

    //Position
    NoPosition(30001, "该位置不存在"),
    DifferentPosition(30002, "不是同一个库房"),
    NoRoom(30003, "该手术室不存在。"),

    //connection Related
    TcpFailException(40001, "TCP连接失败"),
    NoTcpSetupException(40002, "TCP连接未建立"),
    IpOrPortNotValidException(40003, "无效的端口号"),
    TcpCloseFailException(40004, "关闭TCP连接失败"),
    TcpResponseTimeoutException(40005, "TCP返回超时"),
    TcpResponseEOFException(40006, "TCP返回已到达流末尾");

    private final int code;

    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
