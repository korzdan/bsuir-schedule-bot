package by.korzdan.bsuirschedulebot.constansts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReplyKeyboardConstants {
    UNIVERSITY_WEEK("Учебная неделя");

    private final String buttonName;
}
