package com.sample.cafekiosk.spring.api.service.mail;

import com.sample.cafekiosk.spring.api.domain.history.MailSendHistory;
import com.sample.cafekiosk.spring.api.domain.history.MailSendHistoryRepository;
import com.sample.cafekiosk.spring.client.MailSendClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromMail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromMail, toEmail, subject, content);
        if (!result) return false;

        mailSendHistoryRepository.save(MailSendHistory.builder()
                .fromEmail(fromMail)
                .toEmail(toEmail)
                .subject(subject)
                .content(content)
                .build()
        );
        return true;
    }
}
