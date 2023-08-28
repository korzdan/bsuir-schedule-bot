package by.korzdan.bsuirschedulebot.telegram.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NotFoundMessageHandler implements MessageHandler {

    @Override
    public BotApiMethod<Message> handleMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        return new SendMessage(chatId, "Команда не распознана.");
    }

    @Override
    public Boolean isAppropriateMessageHandler(Update update) {
        return false;
    }
}
