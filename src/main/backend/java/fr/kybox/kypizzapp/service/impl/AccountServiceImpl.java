package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.exception.UnauthorizedException;
import fr.kybox.kypizzapp.model.auth.Address;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.repository.AddressRepository;
import fr.kybox.kypizzapp.repository.RegisteredUserRepository;
import fr.kybox.kypizzapp.service.AccountService;
import fr.kybox.kypizzapp.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AuthService authService;
    private final RegisteredUserRepository registeredUserRepository;
    private final AddressRepository addressRepository;

    public AccountServiceImpl(RegisteredUserRepository registeredUserRepository,
                              AuthService authService,
                              AddressRepository addressRepository) {

        this.registeredUserRepository = registeredUserRepository;
        this.authService = authService;
        this.addressRepository = addressRepository;
    }

    @Override
    public RegisteredUser getUserDetails() {

        return null;
    }

    @Override
    public List<Address> findUserAddressList(HttpServletRequest req) {

        RegisteredUser user = getAuthenticatedUser(req);

        if(user == null) throw new UnauthorizedException("The authentication has failed.");
        if(user.getAddressList().isEmpty()) throw new NotFoundException("The user has no address");

        return user.getAddressList();
    }

    @Override
    public Address createNewAddress(Address address, HttpServletRequest req) {

        RegisteredUser user = getAuthenticatedUser(req);
        if(user == null) throw new UnauthorizedException("The authentication has failed.");

        user.getAddressList().add(addressRepository.save(address));
        registeredUserRepository.save(user);

        return address;
    }

    private RegisteredUser getAuthenticatedUser(HttpServletRequest req) {

        return authService.getAuthenticatedUser(req);
    }
}
