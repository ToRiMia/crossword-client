package torimia.client.crosswordclient.version2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import torimia.client.crosswordclient.version1.dto.Region;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private String login;

    private Region region;

    private int guessedWords;

    private int words;

    private int health;

    @Builder.Default
    private List<Character> symbols = new ArrayList<>();
}
