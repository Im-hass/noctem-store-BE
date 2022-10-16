package noctem.storeService.global.security.auth;

import lombok.Getter;
import noctem.storeService.global.security.dto.ClientInfoDto;
import noctem.storeService.store.domain.entity.StoreAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private String loginId;
    private String password;
    private final ClientInfoDto clientInfoDto;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(StoreAccount storeAccount, Collection<? extends GrantedAuthority> authorities) {
        this.id = storeAccount.getId();
        this.loginId = storeAccount.getLoginId();
        this.password = storeAccount.getPassword();
        this.authorities = (List<GrantedAuthority>) authorities;
        this.clientInfoDto = new ClientInfoDto(storeAccount.getId(), storeAccount.getStore().getId(), null, storeAccount.getRole());
    }

    public UserDetailsImpl(ClientInfoDto clientInfoDto, Collection<? extends GrantedAuthority> authorities) {
        this.id = clientInfoDto.getStoreAccountId();
        this.clientInfoDto = clientInfoDto;
        this.authorities = (List<GrantedAuthority>) authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
