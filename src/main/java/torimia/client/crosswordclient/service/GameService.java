package torimia.client.crosswordclient.service;

import lombok.extern.slf4j.Slf4j;
import torimia.client.crosswordclient.HttpClient;
import torimia.client.crosswordclient.dto.GameDto;

@Slf4j
public class GameService {

    private static final String URL = "/game";
    private static final String FIND = "/find";
    private static final String CANCEL = "/cancel";

    private final HttpClient httpClient = new HttpClient();

    public GameDto find(String token) {
        return httpClient.get(URL + FIND, token, GameDto.class);
    }

    public void cancel(String gameId, String token) {
        log.info(httpClient.get(URL + "/" + gameId + CANCEL, token, String.class));
    }
}
