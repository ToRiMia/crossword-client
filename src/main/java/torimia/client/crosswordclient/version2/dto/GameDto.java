package torimia.client.crosswordclient.version2.dto;

import lombok.*;
import torimia.client.crosswordclient.version1.dto.GameStatus;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDto {

    private String gameId;

    private GameStatus status;

    private String winner;

    private Set<PlayerDto> players = new HashSet<>();
}

