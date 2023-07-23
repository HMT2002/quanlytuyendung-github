package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.service.IMailService;
import com.java08.quanlituyendung.service.IThymeleafService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    IThymeleafService thymeleafService;
    @Value("${spring.mail.username}")
    private String email;
    @Override
    public boolean sendVerificationMail(String mail, String code) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(email);

            Map<String,Object> variables= new HashMap<>();
            SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy");
            variables.put("date",sdf.format(new Date()));
            variables.put("code", code);
            helper.setText(thymeleafService.createContent("verify-mail.html",variables),true);
            helper.setTo(mail);
            helper.setSubject("Verification Mail");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
