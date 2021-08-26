package torimia.client.crosswordclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import torimia.client.crosswordclient.dto.TestDto;
import torimia.client.crosswordclient.dto.user.GameDto;

import javax.websocket.OnError;
import javax.websocket.Session;
import java.lang.reflect.Type;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MyStompSessionHandler implements StompSessionHandler {

    private String destination;
    private final String gameId;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        destination = "/topic/crossword.result." + gameId;
        session.subscribe(destination, this);
        log.info("Subscribed to: " + destination);
        session.subscribe("/topic/crossword.test", this);
        log.info("Subscribed to: " + "/topic/crossword.test");
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        log.error("Throwable", throwable);
        System.exit(2);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        log.error("TransportError", throwable);
        System.exit(2);
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        String topic = Optional.ofNullable(stompHeaders.getDestination()).orElse("Topic doesn't exists");
        if (destination.equals(topic)) {
            return GameDto.class;
        } return TestDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload != null) {
            String topic = Optional.ofNullable(headers.getDestination()).orElse("Topic doesn't exists");
            if (destination.equals(topic)) {
                GameDto progress = (GameDto) payload;
                log.info("------------- Received from: " + headers.getDestination() + " -------------");
                log.info("Result: " + progress);
            } else {
                log.info("Unknown object: " + payload.toString());
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("Error for " + session.getId() + " caused by: " + throwable.getMessage());
        throwable.printStackTrace();
        System.exit(2);
    }
}