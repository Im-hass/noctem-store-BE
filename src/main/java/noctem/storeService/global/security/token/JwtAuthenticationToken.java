package noctem.storeService.global.security.token;

import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import noctem.storeService.global.security.auth.UserDetailsImpl;
import noctem.storeService.global.security.dto.ClientInfoDto;
import noctem.storeService.global.security.dto.SecurityLoginReqDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal; // UserDetailsImpl객체 저장
    private String jwt;
    private Object credentials;
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_SIGNER = System.getenv("NOCTEM_JWT_SIGNER");
    public static final String JWT_ISSUER = "Cafe Noctem";
    public static final String JWT_USER_ACCOUNT_ID = "userAccountId";
    public static final String JWT_NICKNAME = "nickname";
    public static final String JWT_EMAIL = "email";
    public static final String JWT_STORE_ACCOUNT_ID = "storeAccountId";
    public static final String JWT_STORE_ID = "storeId";
    public static final String JWT_ROLE = "role";
    public static final String JWT_LOGIN_DTTM = "loginDateTime";

    public JwtAuthenticationToken(SecurityLoginReqDto loginReqDto) {
        super(null);
        this.principal = loginReqDto.getLoginId();
        this.credentials = loginReqDto.getPassword();
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserDetailsImpl userDetails) {
        super(userDetails.getAuthorities());
        this.principal = userDetails;
        this.jwt = generateJwt(userDetails);
        super.setAuthenticated(true);
    }

    private String generateJwt(UserDetailsImpl userDetails) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ClientInfoDto clientInfoDto = userDetails.getClientInfoDto();

        HMACSigner secretKey = HMACSigner.newSHA256Signer(JWT_SIGNER);
        JWT rawJwt = new JWT()
                .setIssuer(JWT_ISSUER)
                .addClaim(JWT_STORE_ACCOUNT_ID, clientInfoDto.getStoreAccountId())
                .addClaim(JWT_STORE_ID, clientInfoDto.getStoreId())
                .addClaim(JWT_ROLE, clientInfoDto.getRole())
                .addClaim(JWT_LOGIN_DTTM, simpleDateFormat.format(new Date()))
                .setExpiration(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusYears(1));
        // 요청헤더 Authorization : Bearer <JWT>
        return String.format("Bearer %s", JWT.getEncoder().encode(rawJwt, secretKey));
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
