package com.watson.demo;


import com.watson.demo.email.service.EmailService;
import com.watson.demo.email.vo.EmailVO;
import com.watson.demo.excel.GeneratorExcelFileService;
import com.watson.demo.excel.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class ExcelTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private GeneratorExcelFileService generatorExcelFileService;

    @Test
    public void sendEmailTest2() {


        EmailVO emailVO = new EmailVO();
        emailVO.setId("1");
        // emailVO.setFrom("watson_von@163.com");
        emailVO.setTo("260482352@qq.com");
        emailVO.setSubject("测试邮箱");
        emailVO.setText("我是watson.von@163.com发来的消息");

        List<User> userList = new ArrayList<>();
        userList.add(new User("watson", new Date(), 1.0f));
        userList.add(new User("von", new Date(), 1.0f));
        userList.add(new User("wen", new Date(), 1.0f));

        File excelFile = generatorExcelFileService.generatorExcel("测试文件", User.class, userList);
        emailVO.setFiles(new File[]{excelFile});

        emailService.sendEmail(emailVO);

        // 不管邮件发送成功还是失败, 都删除生成的附件
        generatorExcelFileService.deleteFile(excelFile);


    }

    @Test
    public void sendEmailTest() {

        EmailVO emailVO = new EmailVO();
        emailVO.setId("1");
        emailVO.setFrom("watson_von@163.com");
        emailVO.setTo("fengxingwen@beerich.cn");
        emailVO.setSubject("测试邮箱");
        File file = new File("C:\\Users\\Fong\\Desktop\\尚硅谷_韩顺平_图解Java设计模式.pdf");
        emailVO.setFiles(new File[]{file});
        emailVO.setText("我是watson.von@163.com发来的消息");
        emailService.sendEmail(emailVO);

    }

}
