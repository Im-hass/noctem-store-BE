package noctem.storeService.global.security.bean;

import noctem.storeService.global.enumeration.Role;
import noctem.storeService.global.security.auth.UserDetailsImpl;
import noctem.storeService.global.security.dto.ClientInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/***
 * 로그인 상태 : 클라이언트의 계정 정보를 리턴
 * 비로그인 상태 : ClientInfoDto 객체대신 null을 리턴
 */
@Component
public class ClientInfoLoader {
    public boolean isAnonymous() {
        return getClientUser() == null;
    }

    public Long getStoreAccountId() {
        if (getClientUser() != null) {
            return getClientUser().getStoreAccountId();
        }
        return null;
    }

    public Long getStoreId() {
        if (getClientUser() != null) {
            return getClientUser().getStoreId();
        }
        return null;
    }

    public Long getUserAccountId() {
        if (getClientUser() != null) {
            return getClientUser().getUserAccountId();
        }
        return null;
    }

    public Role getClientRole() {
        if (getClientUser() != null) {
            return getClientUser().getRole();
        }
        return null;
    }

    private ClientInfoDto getClientUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) authentication.getPrincipal()).getClientInfoDto();
        }
        return null;
    }
}
