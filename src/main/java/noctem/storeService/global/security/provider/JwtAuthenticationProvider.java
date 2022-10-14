package noctem.storeService.global.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.common.SecurityCustomException;
import noctem.storeService.global.security.auth.UserDetailsImpl;
import noctem.storeService.global.security.token.JwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = (String) authentication.getPrincipal();
        String rawPassword = (String) authentication.getCredentials();

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginId);

        //비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            log.warn("Login request: email or password does not match.");
            throw new SecurityCustomException(2017, HttpStatus.BAD_REQUEST);
        }
        return new JwtAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
