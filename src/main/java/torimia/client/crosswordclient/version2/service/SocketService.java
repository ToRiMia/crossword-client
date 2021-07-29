package torimia.client.crosswordclient.version2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import torimia.client.crosswordclient.version1.MyStompSessionHandler;

import java.util.concurrent.ExecutionException;

public class SocketService {

    public static final String URL = "ws://localhost:8080/crossword";

    public StompSession createSession(String token, String gameId) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(converter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(gameId);
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", token);
        return stompClient.connect(URL, headers, sessionHandler).get();
    }

    private static MappingJackson2MessageConverter converter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
