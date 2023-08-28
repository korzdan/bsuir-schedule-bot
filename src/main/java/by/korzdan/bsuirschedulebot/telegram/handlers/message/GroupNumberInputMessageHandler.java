package by.korzdan.bsuirschedulebot.telegram.handlers.message;

import by.korzdan.bsuirschedulebot.connector.BsuirClient;
import by.korzdan.bsuirschedulebot.domain.StudentGroupDto;
import by.korzdan.bsuirschedulebot.domain.UserState;
import by.korzdan.bsuirschedulebot.service.UserService;
import by.korzdan.bsuirschedulebot.telegram.keyboards.InlineGroupNumberApproveKeyboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GroupNumberInputMessageHandler implements MessageHandler {

    private static final String GROUP_FOUND_MSG = """
            Ваш факультет: %s.
            Ваша специальность: %s.
            Номер группы: %s. Курс: %s. Всё верно?""";
    private static final String GROUP_NOT_FOUND = "Такой группы не найдено, пожалуйста, введите номер группы ещё раз.";

    private final UserService userService;
    private final BsuirClient bsuirClient;
    private final InlineGroupNumberApproveKeyboard inlineKeyboard;

    @Override
    public BotApiMethod<Message> handleMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String groupNumber = update.getMessage().getText();
        Long userId = update.getMessage().getFrom().getId();
        return bsuirClient.getStudentGroups().stream()
                .filter(group -> groupNumber.equals(group.getName()))
                .findFirst()
                .map(group -> defineGroupFoundMessageAndChangeUserStatus(chatId, userId, group))
                .orElse(new SendMessage(chatId, GROUP_NOT_FOUND));
    }

    @Override
    public Boolean isAppropriateMessageHandler(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        return userService.checkUserStateByUserIdAndState(userId, UserState.GROUP_NUMBER_INPUT);
    }

    private SendMessage defineGroupFoundMessageAndChangeUserStatus(String chatId, Long userId, StudentGroupDto dto) {
        userService.updateUserStateAndGroupNumberById(userId, UserState.GROUP_NUMBER_APPROVE, dto.getName());
        SendMessage msg = new SendMessage(
                chatId,
                String.format(GROUP_FOUND_MSG,
                        dto.getFacultyAbbrev(),
                        dto.getSpecialityName(),
                        dto.getName(),
                        dto.getCourse())
        );
        msg.setReplyMarkup(inlineKeyboard.defineInlineGroupNumberApproveKeyboard());
        return msg;
    }
}
