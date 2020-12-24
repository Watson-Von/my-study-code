package com.watson.demo.wechat.pay;

import com.thoughtworks.xstream.XStream;
import com.watson.demo.wechat.pay.common.Configure;
import com.watson.demo.wechat.pay.common.HttpRequest;
import com.watson.demo.wechat.pay.common.RandomStringGenerator;
import com.watson.demo.wechat.pay.common.SignUtil;
import com.watson.demo.wechat.pay.model.OrderInfo;
import com.watson.demo.wechat.pay.model.OrderReturnInfo;
import com.watson.demo.wechat.pay.model.QueryOrderResultInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 14:06
 * @Description : 订单查询
 */
@Slf4j
public class Orderquery {

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:33
     * @Description : 主动查询订单状态
     */
    public void orderquery(HttpServletRequest request,
                           HttpServletResponse response,
                           String out_trade_no) throws Exception {

        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        OrderInfo order = new OrderInfo();
        // 小程序ID
        order.setAppid(Configure.getAppID());
        // 商户号
        order.setMch_id(Configure.getMch_id());
        // 随机字符串
        order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        // 商户系统内部订单号, 要求32个字符内, 只能是数字、大小写字母_-|*@, 且在同一个商户号下唯一
        order.setOut_trade_no(out_trade_no);
        // 签名类型
        order.setSign_type("MD5Util");
        // 生成签名
        order.setSign(SignUtil.getSign(order));

        String result = HttpRequest.sendPost(url, order);

        log.info("主动查询订单状态返回的结果:" + result);
        XStream xStream = new XStream();
        xStream.alias("xml", OrderReturnInfo.class);

        QueryOrderResultInfo queryOrderResultInfo = (QueryOrderResultInfo) xStream.fromXML(result);

    }


}
