package by.korzdan.bsuirschedulebot.telegram.handlers;

import by.korzdan.bsuirschedulebot.domain.UserState;
import by.korzdan.bsuirschedulebot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartMessageHandler implements MessageHandler {

    private final UserRepository userRepository;

    @Override
    public BotApiMethod<Message> handleMessage(Update update) {
        Message message = update.getMessage();
        createNewUser(message);
        return new SendMessage(message.getChatId().toString(), "Привет, это телеграм-бот, который точно облегчит" +
                " тебе жизнь с расписанием. Давай для начала зарегистрируемся. Введи свой номер группы:");
    }

    private void createNewUser(Message message) {
        by.korzdan.bsuirschedulebot.domain.User user = new by.korzdan.bsuirschedulebot.domain.User()
                .setId(message.getFrom().getId())
                .setChatId(message.getChatId().toString())
                .setUsername(message.getFrom().getUserName())
                .setUserState(UserState.GROUP_NUMBER_INPUT);
        userRepository.save(user);
    }

    @Override
    public Boolean isAppropriateMessageHandler(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        String messageCommand = update.getMessage().getText();
        return messageCommand.equals("/start") && !userRepository.existsById(userId);
    }
}
