package torimia.client.crosswordclient.version3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {

    private Integer id;

    private String winner;

    private Integer words;

    @Builder.Default
    private List<Character> symbols = new ArrayList<>();
}
