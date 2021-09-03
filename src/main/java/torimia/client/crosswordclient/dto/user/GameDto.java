package torimia.client.crosswordclient.dto.user;

import lombok.*;
import torimia.client.crosswordclient.dto.GameStatus;
import torimia.client.crosswordclient.dto.Region;
import torimia.client.crosswordclient.dto.RoundDto;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDto {

    private String gameId;

    private String winner;

    private GameStatus status;

    private Region region;

    private RoundDto round;

    private Set<CrosswordPlayerDto> players = new HashSet<>();
}

