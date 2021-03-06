package org.example.service.UserServiceTests;

import javassist.NotFoundException;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceMockitoTest {

    private static final String USER_EXIST_NAME = "EXIST";
    private static final String USER_NON_EXIST_NAME = "NON_EXIST";
    private static final List<UserEntity> EXISTING_USER_LIST = List.of(new UserEntity("USER1"),new UserEntity("USER2"));
    private static final List<UserEntity> NON_EXISTING_USER_LIST = List.of(new UserEntity("USER3"),new UserEntity("USER4"));
    private static final Long EXISTING_ID = 100000L;
    private static final Long NON_EXISTING_ID = 900000L;

    @MockBean
    UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        given(userRepositoryMock.findByUsername(USER_EXIST_NAME)).willReturn(Optional.of(new UserEntity(USER_EXIST_NAME)));
        given(userRepositoryMock.findByUsername(USER_NON_EXIST_NAME)).willReturn(null);
        given(userRepositoryMock.findAll()).willReturn(EXISTING_USER_LIST);
        given(userRepositoryMock.findById(EXISTING_ID)).willReturn(Optional.of(new UserEntity("USER")));
    }

    @Test
    public void findByUsernameShouldFindUser() throws NotFoundException {

        UserEntity user = userService.findByUsername(USER_EXIST_NAME);

       assertThat(user).isNotNull();
       assertThat(user.getUsername()).isEqualTo(USER_EXIST_NAME);
    }

    @Test
    public void getAllShouldReturnUserArray() {

        assertThat(userService.getAll()).isNotNull().isEqualTo(EXISTING_USER_LIST);

    }

    @Test
    public void getAllShouldNotBeEqualToArray() {
        assertThat(userService.getAll()).isNotNull().isNotEqualTo(NON_EXISTING_USER_LIST);

    }
    @Test
    public void findByIdShouldFindUser() throws NotFoundException {
        assertThat(userService.findById(EXISTING_ID)).isNotNull();

    }

    @Test
    public void findByIdShouldThrowException() {
        assertThrows(NotFoundException.class, ()-> userService.findById(NON_EXISTING_ID));

    }
}

