package task.manager.task_manager.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.manager.task_manager.exception.UserAlreadyExistsException;
import task.manager.task_manager.jwt.JwtService;
import task.manager.task_manager.security.AppUserDetailsImpl;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

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

        appUserRepository.findByEmail(signUpRequest.email()).ifPresent(appUser -> {
            throw new UserAlreadyExistsException("User already exists with email: " + signUpRequest.email());
        });

        AppUser newUser = new AppUser();
        newUser.setEmail(signUpRequest.email());
        newUser.setRole(Role.valueOf(signUpRequest.role().toUpperCase()));
        newUser.setPassword(passwordEncoder.encode(signUpRequest.password()));

        appUserRepository.save(newUser);

        LOGGER.info("A User With Email: {} Has Been Registered", signUpRequest.email());
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        AppUserDetailsImpl userDetails = (AppUserDetailsImpl) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .userEmail(userDetails.getUsername())
                .token(token)
                .build();
    }
}