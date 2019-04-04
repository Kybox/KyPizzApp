package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<List<RegisteredUser>> searchUser(GenericObject genericObject);

    ResponseEntity<Long> countAllUsers();
}
