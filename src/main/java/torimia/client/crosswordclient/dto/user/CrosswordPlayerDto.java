package torimia.client.crosswordclient.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import torimia.client.crosswordclient.dto.Region;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrosswordPlayerDto {

    private String login;

    private Region region;

    private Integer guessedWords;

    private Integer pointerPosition;

    private Integer winsNumber;
}
