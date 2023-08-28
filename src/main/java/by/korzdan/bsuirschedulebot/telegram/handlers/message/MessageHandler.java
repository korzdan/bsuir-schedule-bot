package by.korzdan.bsuirschedulebot.telegram.handlers.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

    BotApiMethod<Message> handleMessage(Update update);

    Boolean isAppropriateMessageHandler(Update update);
}
