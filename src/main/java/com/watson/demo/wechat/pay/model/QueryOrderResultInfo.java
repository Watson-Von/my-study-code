package com.watson.demo.wechat.pay.model;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 14:34
 * @Description : 查询订单返回的结果
 */
public class QueryOrderResultInfo {

    private String return_code;
    private String return_msg;
    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:38
     * @Description : return_code返回SUCCESS的时候才返回一下字段
     */
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String result_code;

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:38
     * @Description : 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回,
     * 如trade_state不为 SUCCESS, 则只返回out_trade_no(必传)和attach(选传)。
     */
    private String openid;
    // 是否关注公众账号(用户是否关注公众账号, Y-关注, N-未关注)
    private String is_subscribe;
    // 交易类型, 调用接口提交的交易类型, 取值如下: JSAPI, NATIVE, APP, MICROPAY
    private String trade_type;
    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:43
     * @Description : 交易状态
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销（刷卡支付）
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    private String trade_state;
    // 付款银行, 银行类型, 采用字符串类型的银行标识, 具体查看: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_2
    private String bank_type;
    // 标价金额, 订单总金额, 单位为分
    private Integer total_fee;
    // 现金支付金额
    private Integer cash_fee;
    // 微信支付订单号
    private String transaction_id;
    // 商户订单号
    private String out_trade_no;
    // 支付完成时间: 订单支付时间, 格式为yyyyMMddHHmmss, 如2009年12月25日9点10分10秒表示为20091225091010。
    private String time_end;
    // 交易状态描述, 对当前查询订单状态的描述和下一步操作的指引, 如: 支付失败, 请重新下单支付
    private String trade_state_desc;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

}
