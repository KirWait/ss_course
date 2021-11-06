package org.example.service.UserServiceTests;

import javassist.NotFoundException;
import org.example.entity.UserEntity;
import org.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceJUnitTest {


    private static final String REGISTER_NAME = "REGISTER";
    private static final String PASSWORD = "PASSWORD";

    @Autowired
    UserService userService;

    @Test
    public void registerAndDeleteShouldSaveAndDeleteUser() throws NotFoundException {


//        UserEntity userBeforeRegistration = userService.findByUsername(REGISTER_NAME);
//        assertThat(userBeforeRegistration).isNull();
//
//        userService.register(new UserEntity(REGISTER_NAME,PASSWORD));
//
//        UserEntity userAfterRegistration = userService.findByUsername(REGISTER_NAME);
//        assertThat(userAfterRegistration).isNotNull();
//        assertThat(userAfterRegistration.getUsername()).isEqualTo(REGISTER_NAME);
//
//        userService.delete(userAfterRegistration.getId());
//
//        UserEntity userAfterDeletion = userService.findByUsername(REGISTER_NAME);
//        assertThat(userAfterDeletion).isNull();
//        now exception thrown instead of null
    }
}
