package by.korzdan.bsuirschedulebot.telegram.handlers.message;

import by.korzdan.bsuirschedulebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final UserService userService;

    @Override
    public BotApiMethod<Message> handleMessage(Update update) {
        Message message = update.getMessage();
        userService.createNewUserFromTelegramMessage(message);
        return new SendMessage(message.getChatId().toString(), "Привет, это телеграм-бот, который точно облегчит" +
                " тебе жизнь с расписанием. Давай для начала зарегистрируемся. Введи свой номер группы:");
    }

    @Override
    public Boolean isAppropriateMessageHandler(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        String messageCommand = update.getMessage().getText();
        return messageCommand.equals("/start") && !userService.existById(userId);
    }
}
