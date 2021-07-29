package torimia.client.crosswordclient.version1.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDto {

    private String gameId;

    private GameStatus status;

    private int words;

    private String winner = "none";

    @Builder.Default
    private List<Character> symbols = new ArrayList<>();

    @Builder.Default
    private Set<PlayerDto> players = new HashSet<>();
}

