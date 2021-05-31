package com.watson.demo.wechat.pay;

import com.alibaba.fastjson.JSONObject;
import com.watson.demo.wechat.pay.common.Configure;
import com.watson.demo.wechat.pay.common.RandomStringGenerator;
import com.watson.demo.wechat.pay.common.SignUtil;
import com.watson.demo.wechat.pay.model.SignInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 15:05
 * @Description : 再签名
 */
@Slf4j
public class Sign extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Sign() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String repay_id = request.getParameter("repay_id");
            SignInfo signInfo = new SignInfo();

            signInfo.setAppId(Configure.getAppID());
            long time = System.currentTimeMillis() / 1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
            signInfo.setRepay_id("prepay_id=" + repay_id);
            signInfo.setSignType("MD5Util");
            // 生成签名
            String sign = SignUtil.getSign(signInfo);

            JSONObject json = new JSONObject();
            json.put("timeStamp", signInfo.getTimeStamp());
            json.put("nonceStr", signInfo.getNonceStr());
            json.put("package", signInfo.getRepay_id());
            json.put("signType", signInfo.getSignType());
            json.put("paySign", sign);
            log.info("再签名:" + json.toJSONString());
            response.getWriter().append(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-------", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

}
