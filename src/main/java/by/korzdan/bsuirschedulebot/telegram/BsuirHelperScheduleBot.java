package by.korzdan.bsuirschedulebot.telegram;

import by.korzdan.bsuirschedulebot.telegram.handlers.message.MessageHandlerFactory;
import by.korzdan.bsuirschedulebot.telegram.handlers.query.QueryHandlerFactory;
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
    private QueryHandlerFactory queryHandlerFactory;

    public BsuirHelperScheduleBot(
            SetWebhook setWebhook,
            MessageHandlerFactory messageHandlerFactory,
            QueryHandlerFactory queryHandlerFactory) {
        super(setWebhook);
        this.messageHandlerFactory = messageHandlerFactory;
        this.queryHandlerFactory = queryHandlerFactory;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage()) {
            return messageHandlerFactory.handleMessage(update);
        } else if (update.hasCallbackQuery()) {
            return queryHandlerFactory.handleQuery(update);
        }
        return null;
    }
}
