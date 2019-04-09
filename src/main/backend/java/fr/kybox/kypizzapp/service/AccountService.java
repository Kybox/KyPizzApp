package fr.kybox.kypizzapp.service;

import fr.kybox.kypizzapp.model.auth.Address;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AccountService {

    RegisteredUser getUserDetails();

    List<Address> findUserAddressList(HttpServletRequest req);

    Address createNewAddress(Address address, HttpServletRequest req);
}
