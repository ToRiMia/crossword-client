package torimia.client.crosswordclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private String login;

    private Region region;

    private int guessedWords;
}
