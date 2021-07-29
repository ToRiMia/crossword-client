package torimia.client.crosswordclient.version1.service;

import lombok.extern.slf4j.Slf4j;
import torimia.client.crosswordclient.HttpClient;
import torimia.client.crosswordclient.version1.dto.user.AccessTokenDto;
import torimia.client.crosswordclient.version1.dto.user.LoginDto;
import torimia.client.crosswordclient.version1.dto.user.UserDto;

@Slf4j
public class UserService {

    private static final String URL = "/auth";
    private static final String ADMIN = "/admins";
    private static final String LOGIN = "/login";

    private final HttpClient httpClient = new HttpClient();

    public UserDto register(UserDto dto) {
        return httpClient.post(URL, dto, UserDto.class);
    }

    public UserDto registerAdmin(UserDto dto) {
        return httpClient.post(URL + ADMIN, dto, UserDto.class);
    }

    public String login(String login) {
        return "Bearer " + httpClient.post(URL + LOGIN, new LoginDto(login), AccessTokenDto.class).getAccessToken();
    }
}
