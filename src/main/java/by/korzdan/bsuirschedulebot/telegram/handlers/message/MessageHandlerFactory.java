package by.korzdan.bsuirschedulebot.telegram.handlers.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandlerFactory {

    private final List<MessageHandler> messageHandlers;
    private final NotFoundMessageHandler notFoundMessageHandler;

    public BotApiMethod<Message> handleMessage(Update update) {
        return messageHandlers.stream()
                .filter(messageHandler -> messageHandler.isAppropriateMessageHandler(update))
                .findFirst()
                .map(messageHandler -> messageHandler.handleMessage(update))
                .orElseGet(() -> notFoundMessageHandler.handleMessage(update));
    }
}
