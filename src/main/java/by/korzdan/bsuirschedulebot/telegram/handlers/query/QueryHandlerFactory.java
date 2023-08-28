package by.korzdan.bsuirschedulebot.telegram.handlers.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryHandlerFactory {

    private final List<QueryHandler> queryHandlers;

    public BotApiMethod<Message> handleQuery(Update update) {
        return queryHandlers.stream()
                .filter(queryHandler -> queryHandler.isAppropriateQueryHandler(update))
                .findFirst()
                .map(queryHandler -> queryHandler.handleQuery(update))
                .orElse(null);
    }
}
