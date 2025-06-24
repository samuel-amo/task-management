package task.manager.task_manager.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.manager.task_manager.auth.user.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    


    public AuthServiceImpl(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public void register(SignUpRequest signUpRequest) {
        checkIfUserExists(signUpRequest.email());
        AppUser newUser = createUserFromRequest(signUpRequest);
        appUserRepository.save(newUser);
        LOGGER.info("User with email [{}] has been successfully registered.", signUpRequest.email());
    }


    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        AppUserDetailsImpl userDetails = (AppUserDetailsImpl) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new  LoginResponse(
                userDetails.getUsername(),
                token
        );
    }



    private void checkIfUserExists(String email) {
        appUserRepository.findByEmail(email).ifPresent(appUser -> {

            throw new UserAlreadyExistsException(String.format("User with email '%s' already exists.", email));
        });
    }


    private AppUser createUserFromRequest(SignUpRequest signUpRequest) {

        List<String> roles = signUpRequest.roles();
        Set<Role> userRoles = roles
                .stream()
                .map(Role::fromString)
                .collect(Collectors.toSet());

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        return new AppUser(
                signUpRequest.email(),
                encodedPassword,
                userRoles
        );
    }
}
