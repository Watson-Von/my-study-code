package com.watson.demo.wechat.pay;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.watson.demo.wechat.pay.common.HttpRequest;
import com.watson.demo.wechat.pay.common.RandomStringGenerator;
import com.watson.demo.wechat.pay.common.Configure;
import com.watson.demo.wechat.pay.common.SignUtil;
import com.watson.demo.wechat.pay.model.OrderInfo;
import com.watson.demo.wechat.pay.model.OrderReturnInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一下单接口
 */
@Slf4j
public class Xiadan extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Xiadan() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String openid = request.getParameter("openid");
            OrderInfo order = new OrderInfo();
            // 小程序ID
            order.setAppid(Configure.getAppID());
            // 商户号
            order.setMch_id(Configure.getMch_id());
            // 随机字符串
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            // 商品描述
            order.setBody("商品描述");
            // 商户系统内部订单号, 要求32个字符内, 只能是数字、大小写字母_-|*@, 且在同一个商户号下唯一
            order.setOut_trade_no("123456789");
            // 标价金额(分)
            order.setTotal_fee(10);
            // 终端IP(支持IPV4和IPV6两种格式的IP地址, 调用微信支付API的机器IP)
            order.setSpbill_create_ip("123.57.218.54");
            // 通知地址(异步接收微信支付结果通知的回调地址, 通知url必须为外网可访问的url, 不能携带参数)
            order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
            // 交易类型(小程序取值如下: JSAPI)
            order.setTrade_type("JSAPI");
            // 用户标识, 如果 trade_type=JSAPI, 此参数必传, 用户在商户appid下的唯一标识
            order.setOpenid(openid);
            // 签名类型, 默认为MD5
            order.setSign_type("MD5Util");
            // 生成签名
            String sign = SignUtil.getSign(order);
            order.setSign(sign);

            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
            log.info("下单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);

            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            JSONObject json = new JSONObject();
            json.put("prepay_id", returnInfo.getPrepay_id());
            response.getWriter().append(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-------", e);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
