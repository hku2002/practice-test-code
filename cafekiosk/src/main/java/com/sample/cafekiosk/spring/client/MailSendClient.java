package com.sample.cafekiosk.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {

    public boolean sendEmail(String fromMail, String toEmail, String subject, String content) {
        // 메일 서버가 없으므로 로그로 대체
        log.info("메일이 전송되었습니다.");
        return true;
    }
}
