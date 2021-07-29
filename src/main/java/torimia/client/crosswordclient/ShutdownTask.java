package torimia.client.crosswordclient;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import torimia.client.crosswordclient.version1.service.MongoService;

@Slf4j
@RequiredArgsConstructor
public class ShutdownTask extends Thread {

    private static final MongoService mongoService = new MongoService();
    private final String gameId;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000);
        mongoService.deleteGame(gameId);
        log.info("Game with id \"{}\" deleted", gameId);
    }
}
