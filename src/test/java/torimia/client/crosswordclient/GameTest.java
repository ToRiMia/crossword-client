package torimia.client.crosswordclient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import torimia.client.crosswordclient.version1.dto.GameDto;
import torimia.client.crosswordclient.version1.dto.GameStatus;
import torimia.client.crosswordclient.version1.dto.PlayerDto;
import torimia.client.crosswordclient.version1.dto.Region;
import torimia.client.crosswordclient.version1.dto.user.UserDto;
import torimia.client.crosswordclient.version1.service.GameService;
import torimia.client.crosswordclient.version1.service.MongoService;
import torimia.client.crosswordclient.version1.service.UserService;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();
    private final MongoService mongoService = new MongoService("game");

    private static final String USER_LOGIN = "loginLogin";
    private static final GameStatus PENDING = GameStatus.PENDING;
    private static final String USER_REGION_RU = "RU";
    private static final String USER_REGION_EN = "EN";

    private UserDto userDto;
    private String gameId;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .login(USER_LOGIN)
                .build();
    }

    @Test
    void findGameRU() {
        userDto.setRegion(USER_REGION_RU);
        userService.register(userDto);
        String token = userService.login(USER_LOGIN);

        GameDto actual = gameService.find(token);
        gameId = actual.getGameId();

        assertThat(actual)
                .returns(PENDING, GameDto::getStatus);
        assertThat(actual.getPlayers())
                .containsOnly(getExpectedPlayer(USER_REGION_RU));
        assertThat(actual.getWords())
                .isPositive();
        assertThat(matchSymbols(actual.getSymbols(), "^[а-яА-ЯёЁ\\s]+$"))
                .isTrue();
    }

    private PlayerDto getExpectedPlayer(String region) {
        return PlayerDto.builder()
                .login(USER_LOGIN)
                .region(Region.valueOf(region))
                .guessedWords(0)
                .build();
    }

    public static boolean matchSymbols(Collection<Character> symbols, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        String symbolsString = symbols.stream().map(String::valueOf).collect(Collectors.joining(""));
        return pattern.matcher(symbolsString).find();
    }

    @Test
    void findGameEN() {
        userDto.setRegion(USER_REGION_EN);
        userService.register(userDto);
        String token = userService.login(USER_LOGIN);

        GameDto actual = gameService.find(token);
        gameId = actual.getGameId();

        assertThat(actual)
                .returns(PENDING, GameDto::getStatus);
        assertThat(actual.getPlayers())
                .containsOnly(getExpectedPlayer(USER_REGION_EN));
        assertThat(actual.getWords())
                .isPositive();
        assertThat(matchSymbols(actual.getSymbols(), "^[a-zA-Z\\s]+$"))
                .isTrue();
    }


    @Test
    void cancelGame() {
        userDto.setRegion(USER_REGION_RU);
        userService.register(userDto);
        String token = userService.login(USER_LOGIN);

        GameDto actual = gameService.find(token);
        gameId = actual.getGameId();

        gameService.cancel(gameId, token);
        assertThat(mongoService.findById(gameId))
                .isNull();
    }

    @AfterEach
    void tearDown() {
        mongoService.deleteUser(USER_LOGIN);
        mongoService.deleteGame(gameId);
    }
}
