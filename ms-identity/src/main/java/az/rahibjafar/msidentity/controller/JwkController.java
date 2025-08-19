package az.rahibjafar.msidentity.controller;

import az.rahibjafar.msidentity.model.KeyPairModel;
import az.rahibjafar.msidentity.service.JwkService;
import az.rahibjafar.msidentity.service.RsaKeyProvider;
import az.rahibjafar.msidentity.storage.UserDb;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class JwkController {

    private final JwkService jwkService;
    private final RsaKeyProvider rsaKeyProvider;

    public JwkController(JwkService jwkService, RsaKeyProvider rsaKeyProvider) {
        this.jwkService = jwkService;
        this.rsaKeyProvider = rsaKeyProvider;
    }

    @GetMapping(value = "/.well-known/jwks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> jwks() {
        return new JWKSet(jwkService.getAllJwks()).toJSONObject();
    }

    @GetMapping("/changeKeys")
    public ResponseEntity<Void> changeKeys() {
        rsaKeyProvider.reNewKeyPairModel();
        return ResponseEntity.ok().build();
    }
}
