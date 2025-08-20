package az.rahibjafar.msidentity.service;

import az.rahibjafar.msidentity.util.JwtProps;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    private final UserService userService;
    private final RsaKeyProvider rsaKeyProvider;
    @Value("${server.port}")
    private String serverPort;
    private final JwtProps jwtProps;

    public AuthService(UserService userService, RsaKeyProvider rsaKeyProvider, JwtProps jwtProps) {
        this.userService = userService;
        this.rsaKeyProvider = rsaKeyProvider;
        this.jwtProps = jwtProps;
    }

    public String getToken(String username, String password) throws Exception {
        var user = userService.findByUsernameAndPassword(username, password);

        JWSSigner signer = new RSASSASigner(rsaKeyProvider.getRsaKey().toPrivateKey());
        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer(jwtProps.issuer())
                .audience(jwtProps.audience())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(jwtProps.expirySeconds())))
                .claim("scope", jwtProps.scope())
                .claim("roles", user.getRoles())
                .build();

        SignedJWT jwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKeyProvider.getRsaKey().getKeyID()).build(),
                claims
        );
        jwt.sign(signer);

        return jwt.serialize();
    }
}
