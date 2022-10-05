package noctem.storeService.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noctem.storeService.global.enumeration.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoDto {
    private Long id; // UserAccount.id
    private String nickname;
    private String email;
    private Role role;
}
