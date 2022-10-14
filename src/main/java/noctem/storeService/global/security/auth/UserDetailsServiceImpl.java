package noctem.storeService.global.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.SecurityCustomException;
import noctem.storeService.store.domain.entity.StoreAccount;
import noctem.storeService.store.domain.repository.StoreAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StoreAccountRepository storeAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        StoreAccount storeAccount = storeAccountRepository.findByLoginId(loginId);
        // 계정 검증
        if (storeAccount == null) {
            log.warn("Login request: email does not exist or incorrect password");
            throw new SecurityCustomException(2020, HttpStatus.BAD_REQUEST);
        }
        // 계정 권한
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(storeAccount.getRole().toString()));

        return new UserDetailsImpl(storeAccount, roles);
    }
}
