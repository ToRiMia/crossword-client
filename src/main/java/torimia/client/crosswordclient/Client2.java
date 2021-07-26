package torimia.client.crosswordclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import torimia.client.crosswordclient.dto.AccessTokenDto;
import torimia.client.crosswordclient.dto.GuessDto;
import torimia.client.crosswordclient.dto.LoginDto;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Client2 {

    public static final String GAME_ID = "db878410-f401-4a95-b3af-56d1c2539be2"; //enter found gameId
    public static final String USER_LOGIN = "someId222"; //enter created user`s login

    public static final String URL = "ws://localhost:8080/crossword";
    public static final String GUESS_PATH = "/crossword/guess." + GAME_ID;
    public static final String SURRENDER_PATH = "/crossword/surrender." + GAME_ID;
    public static final String AUTH_PATH = "http://localhost:8080/auth/login";

    private static final RestTemplate restTemplate = new RestTemplate();
    private static WebSocketStompClient stompClient;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        WebSocketClient client = new StandardWebSocketClient();

        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(converter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(GAME_ID);
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", getAuthToken());
        StompSession session = stompClient.connect(URL, headers, sessionHandler).get();

        while (true) {
            Thread.sleep(1000);
            System.out.println("Enter suggested word or command \"/surrender\" for surrender:");
            String str = new Scanner(System.in).nextLine();
            if (str.equals("/surrender")) {
                surrender(session);
                Thread.sleep(1000);
                break;
            } else {
                GuessDto guess = new GuessDto(str);
                session.send(GUESS_PATH, guess);
                log.info("Sent: {}", guess);
            }
        }
    }

    private static String getAuthToken() {
        AccessTokenDto token = restTemplate.postForObject(AUTH_PATH, new LoginDto(USER_LOGIN), AccessTokenDto.class);
        log.info("User auth token: {}", token.getAccessToken());
        return String.format("Bearer %s", token.getAccessToken());
    }

    private static void surrender(StompSession session) {
        session.send(SURRENDER_PATH, "");
        log.info("Sent for surrender in game: {}", GAME_ID);
    }

    private static MappingJackson2MessageConverter converter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}

