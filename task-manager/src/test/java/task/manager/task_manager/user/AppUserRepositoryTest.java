package task.manager.task_manager.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void should_find_AppUser_ByEmail() {

        AppUser appUser = new AppUser();
        appUser.setEmail("samuelamo55@outlook.com");

        appUserRepository.save(appUser);
        Optional<AppUser> appUserOptional = appUserRepository.findByEmail("samuelamo55@outlook.com");
        assertTrue(appUserOptional.isPresent());
        assertEquals(appUser.getEmail(), appUserOptional.get().getEmail());


    }

}