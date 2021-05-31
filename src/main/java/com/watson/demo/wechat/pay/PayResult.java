package com.watson.demo.wechat.pay;

import com.watson.demo.wechat.pay.common.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class PayResult extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public PayResult() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reqParams = StreamUtil.read(request.getInputStream());
        log.info("支付结果:" + reqParams);
        response.getWriter().append("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
