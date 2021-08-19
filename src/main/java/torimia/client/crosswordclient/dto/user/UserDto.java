package torimia.client.crosswordclient.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    private String login;
    private String region;

    private Role role;
}
