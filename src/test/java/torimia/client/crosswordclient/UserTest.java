package torimia.client.crosswordclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import torimia.client.crosswordclient.version1.dto.user.Role;
import torimia.client.crosswordclient.version1.dto.user.UserDto;
import torimia.client.crosswordclient.version1.service.MongoService;
import torimia.client.crosswordclient.version1.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private final UserService userService = new UserService();
    private final MongoService mongoService = new MongoService("game1");

    private static final String USER_LOGIN = "loginLogin";
    private static final String USER_REGION_RU = "ru";
    private static final String USER_REGION_EN = "en";
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .login(USER_LOGIN)
                .region(USER_REGION_RU)
                .build();
    }

    @Test
    void createUserWithRegionRU() {
        assertThat(userService.register(userDto))
                .returns(USER_LOGIN, UserDto::getLogin)
                .returns(USER_REGION_RU.toUpperCase(), UserDto::getRegion)
                .returns(Role.ROLE_USER, UserDto::getRole);
        mongoService.deleteUser(USER_LOGIN);
    }

    @Test
    void createUserWithRegionEN() {
        userDto.setRegion(USER_REGION_EN);

        assertThat(userService.register(userDto))
                .returns(USER_LOGIN, UserDto::getLogin)
                .returns(USER_REGION_EN.toUpperCase(), UserDto::getRegion)
                .returns(Role.ROLE_USER, UserDto::getRole);
        mongoService.deleteUser(USER_LOGIN);
    }

    @Test
    void createUserAdmin() {
        assertThat(userService.registerAdmin(userDto))
                .returns(USER_LOGIN, UserDto::getLogin)
                .returns(USER_REGION_RU.toUpperCase(), UserDto::getRegion)
                .returns(Role.ROLE_ADMIN, UserDto::getRole);
        mongoService.deleteUser(USER_LOGIN);
    }

    @Test
    void loginUser() {
        userService.register(userDto);

        assertThat(userService.login(USER_LOGIN))
                .isNotNull();
        mongoService.deleteUser(USER_LOGIN);
    }
}
