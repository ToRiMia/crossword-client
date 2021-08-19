package torimia.client.crosswordclient;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import torimia.client.crosswordclient.service.MongoService;

@Slf4j
@RequiredArgsConstructor
public class ShutdownTask extends Thread {

    private final MongoService mongoService;
    private final String gameId;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(1000);
        Document game = mongoService.findById(gameId);
        if ((game != null) && (!game.get("status").equals("FINISHED"))) {
            if (mongoService.deleteGame(gameId) == 1) {
                log.info("Game with id \"{}\" deleted", gameId);
            } else log.info("Game with id \"{}\" not deleted", gameId);
        }
    }
}
