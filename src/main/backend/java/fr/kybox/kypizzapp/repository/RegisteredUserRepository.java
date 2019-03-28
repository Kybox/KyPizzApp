package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredUserRepository extends ReactiveMongoRepository<RegisteredUser, String> {

    Optional<RegisteredUser> findOneByEmailIgnoreCase(String email);
    Optional<RegisteredUser> findOneByNickName(String nickName);
}
