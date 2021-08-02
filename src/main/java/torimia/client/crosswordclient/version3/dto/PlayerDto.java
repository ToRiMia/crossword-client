package torimia.client.crosswordclient.version3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import torimia.client.crosswordclient.version1.dto.Region;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private String login;

    private Region region;

    private Integer guessedWords;

    private Integer pointerPosition;

    private Integer winsNumber;
}
