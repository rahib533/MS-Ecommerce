package az.rahibjafar.msidentity.service;

import az.rahibjafar.msidentity.model.User;
import az.rahibjafar.msidentity.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
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

    public AuthService(UserService userService, RsaKeyProvider rsaKeyProvider) {
        this.userService = userService;
        this.rsaKeyProvider = rsaKeyProvider;
    }

    public String getToken(String username, String password) throws Exception {
        var user = userService.findByUsernameAndPassword(username, password);

        JWSSigner signer = new RSASSASigner(rsaKeyProvider.getRsaKey().toPrivateKey());
        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("http://localhost:" + serverPort)
                .audience(List.of("service1"))
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600)))
                .claim("scope", "svc1.read svc1.write")
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
