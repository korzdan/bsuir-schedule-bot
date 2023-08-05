package by.korzdan.bsuirschedulebot.telegram;

import by.korzdan.bsuirschedulebot.telegram.handlers.MessageHandlerFactory;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class BsuirHelperScheduleBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;

    private MessageHandlerFactory messageHandlerFactory;

    public BsuirHelperScheduleBot(SetWebhook setWebhook, MessageHandlerFactory messageHandlerFactory) {
        super(setWebhook);
        this.messageHandlerFactory = messageHandlerFactory;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageHandlerFactory.handleMessage(update);
    }
}
