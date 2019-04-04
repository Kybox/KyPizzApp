package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.model.GenericObject;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.kybox.kypizzapp.utils.constant.ValueObject.SEARCH_USER_KEY;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final RegisteredUserRepository registeredUserRepository;

    @Autowired
    public UserServiceImpl(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    @Override
    public ResponseEntity<List<RegisteredUser>> searchUser(GenericObject genericObject) {

        String keyword = genericObject.getData();

        if(keyword == null || keyword.isEmpty())
            return ResponseEntity.badRequest().build();

        List<RegisteredUser> userList = new ArrayList<>();

        if(keyword.contains("@"))
            userList.addAll(registeredUserRepository.findAllByEmail(keyword));
        else userList.addAll(registeredUserRepository.findAllByNickNameIgnoreCase(keyword));

        if(userList.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(userList);
    }

    @Override
    public ResponseEntity<Long> countAllUsers() {

        return ResponseEntity.ok(registeredUserRepository.count());
    }
}
