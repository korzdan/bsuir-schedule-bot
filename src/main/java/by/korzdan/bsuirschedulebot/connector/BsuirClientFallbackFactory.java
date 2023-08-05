package by.korzdan.bsuirschedulebot.connector;

import by.korzdan.bsuirschedulebot.domain.StudentGroupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collections;
import java.util.List;

@Slf4j
public class BsuirClientFallbackFactory implements FallbackFactory<BsuirClient> {

    @Override
    public BsuirClient create(Throwable cause) {
        return new BsuirClient() {

            @Override
            public List<StudentGroupDto> getStudentGroups() {
                log.error("getStudentGroups error: {}", cause.getMessage());
                return Collections.emptyList();
            }
        };
    }
}
