package by.korzdan.bsuirschedulebot.config;

import by.korzdan.bsuirschedulebot.telegram.BsuirHelperScheduleBot;
import by.korzdan.bsuirschedulebot.config.settings.TelegramSettings;
import by.korzdan.bsuirschedulebot.telegram.handlers.message.MessageHandlerFactory;
import by.korzdan.bsuirschedulebot.telegram.handlers.query.QueryHandlerFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class BotConfig {

    private final TelegramSettings telegramSettings;
    private final MessageHandlerFactory messageHandlerFactory;
    private final QueryHandlerFactory queryHandlerFactory;

    @Bean
    public SetWebhook webhook() {
        return SetWebhook.builder()
                .url(telegramSettings.getWebhookPath())
                .build();
    }

    @Bean
    public BsuirHelperScheduleBot bsuirHelperScheduleBot() {
        return new BsuirHelperScheduleBot(webhook(), messageHandlerFactory, queryHandlerFactory);
    }
}
