package noctem.storeService.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityLoginReqDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
