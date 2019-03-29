package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.auth.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {

    Authority findFirstByName(String name);
}
