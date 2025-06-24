package task.manager.task_manager.auth.user;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private boolean isEnabled = true;
    private boolean isAccountLocked = false;
    private boolean isAccountExpired = false;
    private boolean isCredentialsExpired = false;

    public AppUser() {
    }


    public AppUser(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    public boolean isAccountExpired() {
        return isAccountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        isAccountExpired = accountExpired;
    }

    public boolean isCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(email, appUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {

        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", isEnabled=" + isEnabled +
                ", isAccountLocked=" + isAccountLocked +
                '}';
    }
}