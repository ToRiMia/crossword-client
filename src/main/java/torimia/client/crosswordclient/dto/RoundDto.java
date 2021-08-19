package torimia.client.crosswordclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {

    private Integer id;

    private String winner;

    private Instant finishTime;

    private Integer words;

    @Builder.Default
    private List<Character> symbols = new ArrayList<>();
}
