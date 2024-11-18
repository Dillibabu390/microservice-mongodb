package com.ncash.emailservice.mailing;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    @Value("${mail.config.to-mail}")
    private String toMail;


    //todo need to implement dynamic email representaion securitycontextholder to get user email by authenticated user
    public void sendEmail(Map<String, Object> emailModel, boolean orderStatus) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            // Set up the MimeMessageHelper for sending HTML email
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name()
            );

            // Determine the template to use based on order status
            Template template;
            String html;

            if (orderStatus) {
                // Load "Order Confirmed" template for confirmed orders
                template = config.getTemplate("orderconform.ftl");
                html = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailModel);
            } else {
                // Load "Order Failed" template for failed orders (or other status)
                template = config.getTemplate("orderfailed.ftl"); // Assuming a failed template exists
                html = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailModel);
            }

            // Set the recipient, subject, and email body
            helper.setTo(toMail);
            helper.setText(html, true);  // Set HTML content
            helper.setSubject(orderStatus ? "Order Confirmation" : "Order Failed");  // Set dynamic subject

            // Send the email
            javaMailSender.send(message);

            log.info("Email sent successfully");

        } catch (MessagingException | IOException | TemplateException e) {
            // Enhanced error logging for better tracking of failures
            log.error("Unexpected error occurred while sending email: {}", e.getMessage(), e);
        }
    }

}