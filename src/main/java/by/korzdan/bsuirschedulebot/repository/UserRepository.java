package by.korzdan.bsuirschedulebot.repository;

import by.korzdan.bsuirschedulebot.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

}
