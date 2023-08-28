package by.korzdan.bsuirschedulebot.telegram.handlers.query;

import by.korzdan.bsuirschedulebot.domain.UserState;
import by.korzdan.bsuirschedulebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GroupNumberApproveQueryHandler implements QueryHandler {

    private final UserService userService;

    @Override
    public BotApiMethod<Message> handleQuery(Update update) {
        String data = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getFrom().getId();
        if (data.equals("1")) {
            userService.updateUserState(userId, UserState.REGISTRATION_COMPLETE);
            return new SendMessage(userId.toString(), "Вы успешно зарегистрировались! Теперь для вас доступно меню с некоторыми функциями.");
        } else if (data.equals("2")) {
            userService.updateUserStateAndGroupNumberById(userId, UserState.GROUP_NUMBER_INPUT, null);
            return new SendMessage(userId.toString(), "Введите номер своей группы снова.");
        }
        return null;
    }

    @Override
    public Boolean isAppropriateQueryHandler(Update update) {
        Long userId = update.getCallbackQuery().getFrom().getId();
        return userService.checkUserStateByUserIdAndState(userId, UserState.GROUP_NUMBER_APPROVE);
    }
}
