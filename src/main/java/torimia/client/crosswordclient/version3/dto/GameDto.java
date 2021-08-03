package torimia.client.crosswordclient.version3.dto;

import lombok.*;
import torimia.client.crosswordclient.version1.dto.GameStatus;
import torimia.client.crosswordclient.version1.dto.Region;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDto {

    private String gameId;

    private GameStatus status;

    private RoundDto round;

    private Region region;

    private Set<PlayerDto> players = new HashSet<>();
}

