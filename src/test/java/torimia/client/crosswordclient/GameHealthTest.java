package torimia.client.crosswordclient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import torimia.client.crosswordclient.dto.GameStatus;
import torimia.client.crosswordclient.dto.Region;
import torimia.client.crosswordclient.dto.user.CrosswordPlayerDto;
import torimia.client.crosswordclient.dto.user.GameDto;
import torimia.client.crosswordclient.dto.user.UserDto;
import torimia.client.crosswordclient.service.GameService;
import torimia.client.crosswordclient.service.MongoService;
import torimia.client.crosswordclient.service.UserService;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GameHealthTest {

    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();
    private final MongoService mongoService = new MongoService("game2");

    private static final String USER_LOGIN = "loginLogin";
    private static final GameStatus PENDING = GameStatus.PENDING;
    private static final String USER_REGION_RU = "RU";
    private static final String USER_REGION_EN = "EN";
    private static final int HEALTH_NUMBER = 100;

    private UserDto userDto;
    private String gameId;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .login(USER_LOGIN)
                .build();
    }

    @Disabled
    @Test
    void findGameRU() {
        userDto.setRegion(USER_REGION_RU);
        userService.register(userDto);
        String token = userService.login(USER_LOGIN);

        GameDto actual = gameService.find(token);
        gameId = actual.getGameId();

        assertThat(actual)
                .returns(PENDING, GameDto::getStatus);

        assertThat(actual.getPlayers().stream().findFirst())
                .isPresent()
                .get()
                .returns(USER_LOGIN, CrosswordPlayerDto::getLogin)
                .returns(Region.RU, CrosswordPlayerDto::getRegion);
//                .returns(HEALTH_NUMBER, CrosswordPlayerDto::getHealth);

//        List<Character> symbols = actual.getPlayers().stream().findFirst().get().getSymbols();
//        assertThat(matchSymbols(symbols, "^[??-????-??????\\s]+$"))
//                .isTrue();
    }

    public static boolean matchSymbols(Collection<Character> symbols, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        String symbolsString = symbols.stream().map(String::valueOf).collect(Collectors.joining(""));
        return pattern.matcher(symbolsString).find();
    }

    @Disabled
    @Test
    void findGameEN() {
        userDto.setRegion(USER_REGION_EN);
        userService.register(userDto);
        String token = userService.login(USER_LOGIN);

        GameDto actual = gameService.find(token);
        gameId = actual.getGameId();

        assertThat(actual)
                .returns(PENDING, GameDto::getStatus);

        assertThat(actual.getPlayers().stream().findFirst())
                .isPresent()
                .get()
                .returns(USER_LOGIN, CrosswordPlayerDto::getLogin)
                .returns(Region.EN, CrosswordPlayerDto::getRegion);
//                .returns(HEALTH_NUMBER, CrosswordPlayerDto::getHealth);
//
//        List<Character> symbols = actual.getPlayers().stream().findFirst().get().getSymbols();
//        assertThat(matchSymbols(symbols, "^[a-zA-Z\\s]+$"))
//                .isTrue();
    }

    @Disabled
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
