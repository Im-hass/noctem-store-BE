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

    public boolean isUser() {
        return getUserAccountId() != null;
    }

    public boolean isStore() {
        return getStoreAccountId() != null;
    }

    public Long getUserAccountId() {
        return getClientUser() == null ? null : getClientUser().getUserAccountId();
    }

    public String getUserNickname() {
        return getClientUser() == null ? null : getClientUser().getNickname();
    }

    public String getUserEmail() {
        return getClientUser() == null ? null : getClientUser().getEmail();
    }

    public Long getStoreAccountId() {
        return getClientUser() == null ? null : getClientUser().getStoreAccountId();
    }

    public Long getStoreId() {
        return getClientUser() == null ? null : getClientUser().getStoreId();
    }

    public Role getClientRole() {
        return getClientUser() == null ? null : getClientUser().getRole();
    }

    private ClientInfoDto getClientUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) authentication.getPrincipal()).getClientInfoDto();
        }
        return null;
    }
}