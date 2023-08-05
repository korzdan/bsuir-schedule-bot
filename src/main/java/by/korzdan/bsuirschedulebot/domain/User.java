package by.korzdan.bsuirschedulebot.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Accessors(chain = true)
public class User {
    @Id
    private Long id;
    private String chatId;
    private String username;
    private Integer groupNumber;
    private UserState userState;
    private Boolean isRegistrationComplete;
}
