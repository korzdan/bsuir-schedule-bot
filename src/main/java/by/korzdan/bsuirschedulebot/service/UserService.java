package by.korzdan.bsuirschedulebot.service;

import by.korzdan.bsuirschedulebot.domain.User;
import by.korzdan.bsuirschedulebot.domain.UserState;
import by.korzdan.bsuirschedulebot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    public User updateUserStateAndGroupNumberById(Long id, UserState state, String groupNumber) {
        return userRepository.findById(id)
                .map(user -> user.setUserState(state).setGroupNumber(groupNumber))
                .map(userRepository::save)
                .orElseThrow();
    }

    public void updateUserState(Long id, UserState state) {
        userRepository.findById(id)
                .map(user -> user.setUserState(state))
                .map(userRepository::save)
                .orElseThrow();
    }

    public boolean checkUserStateByUserIdAndState(Long id, UserState state) {
        return userRepository.findById(id)
                .filter(user -> state.equals(user.getUserState()))
                .isPresent();
    }

    public User createNewUserFromTelegramMessage(Message message) {
        by.korzdan.bsuirschedulebot.domain.User user = new by.korzdan.bsuirschedulebot.domain.User()
                .setId(message.getFrom().getId())
                .setChatId(message.getChatId().toString())
                .setUsername(message.getFrom().getUserName())
                .setUserState(UserState.GROUP_NUMBER_INPUT);
        return userRepository.save(user);
    }
}
