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
    private Long id; // StoreAccount.id
    private Long storeId;
    private Role role;
}
