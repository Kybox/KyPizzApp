package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Override
    public RegisteredUser getUserDetails() {

        return null;
    }
}
