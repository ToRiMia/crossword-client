package torimia.client.crosswordclient;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import torimia.client.crosswordclient.version1.service.MongoService;

@Slf4j
@RequiredArgsConstructor
public class ShutdownTask extends Thread {

    private final MongoService mongoService;
    private final String gameId;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000);
        if (mongoService.deleteGame(gameId) == 1) {
            log.info("Game with id \"{}\" deleted", gameId);
        } else log.info("Game with id \"{}\" not deleted", gameId);
    }
}
