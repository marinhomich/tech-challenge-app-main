package com.oficina.adapter.out.notification;

import com.oficina.domain.model.Customer;
import com.oficina.domain.model.Order;
import com.oficina.domain.port.out.NotificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Implementação da porta de notificação via e-mail.
 * Se o cliente não tiver e-mail cadastrado, ou se o envio falhar, faz log do status.
 */
@Component
public class EmailNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationAdapter.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:noreply@oficina.local}")
    private String from;

    public EmailNotificationAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void notifyOrderStatus(Order order, Customer customer) {
        String statusLabel = translateStatus(order.getStatus());
        String body = String.format(
                "Olá, %s!\n\nSua Ordem de Serviço nº %s está com o seguinte status: %s.\n\nObrigado pela confiança!",
                customer.getName(), order.getNumber(), statusLabel);

        log.info("[Notification] OS={} Customer={} Status={}", order.getNumber(),
                customer.getName(), order.getStatus());

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            log.warn("[Notification] Cliente sem e-mail cadastrado. Notificação apenas via log.");
            return;
        }

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(customer.getEmail());
            msg.setSubject("Atualização da Ordem de Serviço " + order.getNumber());
            msg.setText(body);
            mailSender.send(msg);
            log.info("[Notification] E-mail enviado para {}", customer.getEmail());
        } catch (MailException ex) {
            log.warn("[Notification] Falha ao enviar e-mail para {}: {}. Status registrado em log.",
                    customer.getEmail(), ex.getMessage());
        }
    }

    private String translateStatus(String status) {
        return switch (status) {
            case "RECEIVED" -> "Recebida";
            case "IN_DIAGNOSTIC" -> "Em Diagnóstico";
            case "AWAITING_APPROVAL" -> "Aguardando Aprovação";
            case "IN_EXECUTION" -> "Em Execução";
            case "FINISHED" -> "Finalizada";
            case "DELIVERED" -> "Entregue";
            default -> status;
        };
    }
}
