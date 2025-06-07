package task.manager.task_manager.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import task.manager.task_manager.user.AppUser;
import task.manager.task_manager.user.AppUserRepository;

@Service
public class AppUserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserDetailServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        return new AppUserDetailsImpl(appUser);
    }
}
