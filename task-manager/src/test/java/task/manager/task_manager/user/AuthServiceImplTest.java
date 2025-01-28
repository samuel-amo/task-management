package task.manager.task_manager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import task.manager.task_manager.exception.UserAlreadyExistsException;
import task.manager.task_manager.jwt.JwtService;
import task.manager.task_manager.security.AppUserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private AppUser appUser;
    private AppUserDetails appUserDetails;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest("samuelamo55@outlook.com", "password");
        loginRequest = new LoginRequest("samuelamo55@outlook.com", "password");

        appUser = new AppUser(1L, "samuelamo55@outlook.com", "encodedPassword");
        appUserDetails = new AppUserDetails(appUser);
    }

    @Test
    void should_register_user_that_does_not_exist() {
        when(appUserRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequest.password())).thenReturn("encodedPassword");

        authService.register(signUpRequest);

        verify(appUserRepository).save(any(AppUser.class));
    }

    @Test
    void should_throw_exception_when_register_user_that_already_exists() {
        when(appUserRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.of(appUser));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(signUpRequest));
    }

    @Test
    void should_login_successfully() {

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(appUserDetails);
        when(jwtService.generateToken(appUserDetails)).thenReturn("jwtToken");


        LoginResponse response = authService.login(loginRequest);


        assertEquals("samuelamo55@outlook.com", response.userEmail());
        assertEquals("jwtToken", response.token());
    }

    @Test
    void should_throw_exception_when_login_fails() {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }
}
