package lv.javaguru.java2.businesslogic.user;

import lv.javaguru.java2.businesslogic.TemplateService;
import lv.javaguru.java2.businesslogic.serviceexception.IllegalRequestException;
import lv.javaguru.java2.businesslogic.serviceexception.ServiceException;
import lv.javaguru.java2.businesslogic.validators.UserProfileFormatValidationService;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.dto.UserProfile;
import lv.javaguru.java2.dto.builders.UserProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final String USER_ALREADY_EXISTS = "User already exists";
    private final static String ADMIN_EMAIL = "admin@miska.lv";
    private final static String ADMIN_PASSWORD = "miska";

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private UserProfileFormatValidationService userProfileFormatValidationService;

    @Autowired
    private UserProfileUtil userProfileUtil;

    @Autowired
    private TemplateService templateService;


    @Override
    public boolean allowRegistration() {
        return !userProvider.authorized();
    }

    @Override
    public Map<String, Object> model() throws ServiceException {
        if (!allowRegistration()) {
            throw new IllegalRequestException();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(templateService.model());
        return map;
    }

    @Override
    public User register(UserProfile userProfile) throws ServiceException {
        if (userProvider.authorized())
            throw new IllegalRequestException();

        userProfileFormatValidationService.validate(userProfile);
        User alreadyExists = userDAO.getByEmail(userProfile.getEmail());
        if (alreadyExists != null || userProfile.getEmail().equals(ADMIN_EMAIL) &&
                                     userProfile.getPassword().equals(ADMIN_PASSWORD)) {
            throw new ServiceException(USER_ALREADY_EXISTS);
        }
        User newUser = userProfileUtil.buildUser(userProfile);
        newUser.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        userDAO.create(newUser);
        return newUser;
    }
}
