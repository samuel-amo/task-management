package task.manager.task_manager.auth.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class AppUserDetailsImpl implements UserDetails {

    private final AppUser appUser;

    public AppUserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return appUser
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !appUser.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !appUser.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !appUser.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return appUser.isEnabled();
    }
}
