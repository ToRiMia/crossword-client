package torimia.client.crosswordclient.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompSession;
import torimia.client.crosswordclient.MyStompSessionHandler;
import torimia.client.crosswordclient.ShutdownTask;
import torimia.client.crosswordclient.dto.GuessDto;
import torimia.client.crosswordclient.service.GameService;
import torimia.client.crosswordclient.service.MongoService;
import torimia.client.crosswordclient.service.SocketService;
import torimia.client.crosswordclient.service.UserService;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ClientRound {

    public static final String USER_LOGIN = "1"; //enter created user`s login
    public static final String GUESS_PATH = "/crossword/guess.";
    public static final String SURRENDER_PATH = "/crossword/surrender.";

    private static final UserService userService = new UserService();
    private static final GameService gameService = new GameService();
    private static final SocketService socketService = new SocketService();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String token = userService.login(USER_LOGIN);
        String gameId = gameService.find(token).getGameId();

        Runtime.getRuntime().addShutdownHook(new ShutdownTask(new MongoService("game"), gameId));

        StompSession session = socketService.createSession(token, new MyStompSessionHandler(gameId));

        while (true) {
            Thread.sleep(1000);
            System.out.println("Enter suggested word or command \"/surrender\" for surrender:");
            String str = new Scanner(System.in).nextLine();
            if (processCommand(gameId, session, str)) break;
        }
    }

    private static boolean processCommand(String gameId, StompSession session, String str) throws InterruptedException {
        switch (str) {
            case "/surrender": {
                surrender(session, gameId);
                Thread.sleep(1000);
                return true;
            }
            case "/stop": {
                return true;
            }
            default: {
                GuessDto guess = new GuessDto(str);
                session.send(GUESS_PATH + gameId, guess);
                log.info("Sent: {}", guess);
                return false;
            }
        }
    }

    private static void surrender(StompSession session, String gameId) {
        session.send(SURRENDER_PATH + gameId, "");
        log.info("Sent for surrender in game: {}", gameId);
    }
}

