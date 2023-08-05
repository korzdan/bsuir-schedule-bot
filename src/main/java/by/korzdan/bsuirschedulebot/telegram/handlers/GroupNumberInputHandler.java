package by.korzdan.bsuirschedulebot.telegram.handlers;

import by.korzdan.bsuirschedulebot.connector.BsuirClient;
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
public class GroupNumberInputHandler implements MessageHandler {

    private final UserRepository userRepository;
    private final BsuirClient bsuirClient;

    @Override
    public BotApiMethod<Message> handleMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String groupNumber = update.getMessage().getText();
        return bsuirClient.getStudentGroups().stream()
                .filter(group -> groupNumber.equals(group.getName()))
                .findFirst()
                .map(group -> new SendMessage(chatId, "Вы уверены, что ваша группа: " + group.getName() + "?"))
                .orElse(new SendMessage(chatId, "Такой группы не найдено, пожалуйста, введите номер группы ещё раз:"));
    }

    @Override
    public Boolean isAppropriateMessageHandler(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        return userRepository.findById(userId)
                .map(user -> UserState.GROUP_NUMBER_INPUT.equals(user.getUserState()))
                .orElse(false);
    }
}
