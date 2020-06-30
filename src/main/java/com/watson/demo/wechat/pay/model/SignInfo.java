package com.watson.demo.wechat.pay.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 15:05
 * @Description : 签名信息
 */
public class SignInfo {

    // 小程序ID
    private String appId;
    // 时间戳
    private String timeStamp;
    // 随机串
    private String nonceStr;
    @XStreamAlias("package")
    private String repay_id;
    // 签名方式
    private String signType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getRepay_id() {
        return repay_id;
    }

    public void setRepay_id(String repay_id) {
        this.repay_id = repay_id;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }


}
