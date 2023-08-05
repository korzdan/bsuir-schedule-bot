package by.korzdan.bsuirschedulebot.connector;

import by.korzdan.bsuirschedulebot.domain.StudentGroupDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "bsuir", url = "${bsuir.base-url}", fallbackFactory = BsuirClientFallbackFactory.class)
public interface BsuirClient {

    @GetMapping("${bsuir.student-groups-uri}")
    List<StudentGroupDto> getStudentGroups();
}
