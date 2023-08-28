package by.korzdan.bsuirschedulebot.telegram.handlers.query;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface QueryHandler {

    BotApiMethod<Message> handleQuery(Update update);

    Boolean isAppropriateQueryHandler(Update update);
}
