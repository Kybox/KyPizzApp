package fr.kybox.kypizzapp.repository;

import fr.kybox.kypizzapp.model.auth.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
}
