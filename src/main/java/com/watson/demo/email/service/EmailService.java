package com.watson.demo.email.service;

import com.watson.demo.email.vo.EmailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送邮件
     */
    public EmailVO sendEmail(EmailVO emailVo) {
        try {
            // 检测邮件
            checkEmail(emailVo);
            // 发送邮件
            sendMimeMail(emailVo);
            // 保存邮件
            return saveEmail(emailVo);
        } catch (Exception e) {
            // 打印错误信息
            log.error("发送邮件失败:", e);
            emailVo.setStatus("fail.....");
            emailVo.setErrorMsg(e.getMessage());
            return emailVo;
        }

    }

    // 检测邮件信息类
    private void checkEmail(EmailVO emailVo) {
        if (StringUtils.isEmpty(emailVo.getTo())) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(emailVo.getSubject())) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(emailVo.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    // 构建复杂邮件信息类
    private void sendMimeMail(EmailVO emailVo) {
        try {
            // true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            // 邮件发信人从配置项读取
            emailVo.setFrom(getMailSendFrom());
            // 邮件发信人
            messageHelper.setFrom(getMailSendFrom());
            // 邮件收信人
            messageHelper.setTo(emailVo.getTo().split(","));
            // 邮件主题
            messageHelper.setSubject(emailVo.getSubject());
            // 邮件内容
            messageHelper.setText(emailVo.getText());
            // 抄送
            if (StringUtils.isNotEmpty(emailVo.getCc())) {
                messageHelper.setCc(emailVo.getCc().split(","));
            }
            // 密送
            if (StringUtils.isNotEmpty(emailVo.getBcc())) {
                messageHelper.setCc(emailVo.getBcc().split(","));
            }
            // 添加邮件附件
            if (emailVo.getFiles() != null) {
                for (File file : emailVo.getFiles()) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 发送时间
            if (emailVo.getSentDate() == null) {
                emailVo.setSentDate(new Date());
                messageHelper.setSentDate(emailVo.getSentDate());
            }
            // 正式发送邮件
            mailSender.send(messageHelper.getMimeMessage());
            emailVo.setStatus("ok");
            log.info("发送邮件成功：{}->{}", emailVo.getFrom(), emailVo.getTo());
        } catch (Exception e) {
            // 发送失败
            throw new RuntimeException(e);
        }
    }

    // 保存邮件
    private EmailVO saveEmail(EmailVO emailVo) {
        // 将邮件保存到数据库
        return emailVo;
    }

    // 获取邮件发信人
    private String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }

}
