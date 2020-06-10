package com.watson.demo;

import com.watson.demo.email.service.EmailService;
import com.watson.demo.email.vo.EmailVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest() {

        EmailVO emailVO = new EmailVO();
        emailVO.setId("1");
        emailVO.setFrom("watson_von@163.com");
        emailVO.setTo("260482352@qq.com");
        emailVO.setSubject("测试邮箱");
        File file = new File("C:\\Users\\Fong\\Desktop\\尚硅谷_韩顺平_图解Java设计模式.pdf");
        emailVO.setFiles(new File[]{file});
        emailVO.setText("我是watson.von@163.com发来的消息");
        emailService.sendEmail(emailVO);

    }

}
