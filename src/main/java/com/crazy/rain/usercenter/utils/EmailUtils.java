package com.crazy.rain.usercenter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * @ClassName: EmailUtils
 * @Description: 发送邮件工具类
 * @author: CrazyRain
 * @date: 2024/4/15 上午11:17
 */
@Slf4j
@Component
public class EmailUtils {

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private MailProperties mailProperties;

    public int sendVerificationCode(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailProperties.getUsername());
        msg.setTo(email);
        int code = RandomNumberUtils.obtainVerificationCode();
        msg.setText("验证码:" + code);
        mailSender.send(msg);
        return code;
    }

    public boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }


}
