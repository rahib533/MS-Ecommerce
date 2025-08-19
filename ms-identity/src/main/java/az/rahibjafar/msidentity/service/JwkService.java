package az.rahibjafar.msidentity.service;

import az.rahibjafar.msidentity.model.AuthKey;
import az.rahibjafar.msidentity.storage.UserDb;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwkService {

    public List<JWK> getAllJwks(){
        List<JWK> jwks = new ArrayList<>();

        UserDb.getAuthKeys().forEach(authKey -> {
            jwks.add(authKey.getRsaKey().toPublicJWK());
        });

        return jwks;
    }

}
