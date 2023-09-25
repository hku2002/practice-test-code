package com.sample.cafekiosk.spring.api.service.mail;

import com.sample.cafekiosk.spring.api.domain.history.MailSendHistory;
import com.sample.cafekiosk.spring.api.domain.history.MailSendHistoryRepository;
import com.sample.cafekiosk.spring.client.MailSendClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    MailSendClient mailSendClient;

    @Mock
    MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMail() {
        // given
        given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).willReturn(true);

        // when
        boolean result = mailService.sendMail(anyString(), anyString(), anyString(), anyString());

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}