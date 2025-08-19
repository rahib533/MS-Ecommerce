package az.rahibjafar.msidentity.service;

import az.rahibjafar.msidentity.model.AuthKey;
import az.rahibjafar.msidentity.model.AuthKeyStatus;
import az.rahibjafar.msidentity.model.KeyPairModel;
import az.rahibjafar.msidentity.storage.UserDb;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
public class RsaKeyProvider {
    private volatile RSAKey rsaKey;

    public KeyPairModel rsaKeyPair() {
        try {
            if (UserDb.getAuthKeys().size() == 0){
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);
                KeyPair pair = keyGen.generateKeyPair();

                PrivateKey privateKey = pair.getPrivate();
                PublicKey publicKey = pair.getPublic();
                String keyId = UUID.randomUUID().toString();
                System.out.println("KeyId: " + keyId);

                String encodedPublicKey = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(publicKey.getEncoded());
                String encodedPrivateKey = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(privateKey.getEncoded());

                UserDb.addAuthKey(new AuthKey(keyId, encodedPrivateKey, encodedPublicKey, AuthKeyStatus.ACTIVE));

                return new KeyPairModel(keyId ,privateKey, publicKey);
            }
            else{
                AuthKey authKey = UserDb.getAuthKeys().stream().filter(key -> key.getKeyStatus().equals(AuthKeyStatus.ACTIVE)).findFirst().orElse(
                        new AuthKey()
                );

                byte[] decoded = Base64.getDecoder().decode(authKey.getPublicKey().replaceAll("\\s+", ""));
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(keySpec);

                decoded = Base64.getDecoder().decode(authKey.getPrivateKey().replaceAll("\\s+", ""));

                PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decoded);
                keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(keySpecPrivate);

                return new KeyPairModel(authKey.getkId(), privateKey, publicKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void rsaJwk() {
        KeyPairModel keyPairModel = rsaKeyPair();
        RSAPublicKey pub = (RSAPublicKey) keyPairModel.getPublicKey();
        RSAKey rsaKey = new RSAKey.Builder(pub)
                .privateKey(rsaKeyPair().getPrivateKey())
                .keyID(keyPairModel.getkId())
                .build();
        keyPairModel.setRsaKey(rsaKey);

        AuthKey authKey = UserDb.getAuthKeys().stream().filter(key -> key.getkId().equals(keyPairModel.getkId())).findFirst().orElse(
                new AuthKey()
        );
        authKey.setRsaKey(rsaKey);
        this.rsaKey = rsaKey;
    }

    public RSAKey getRsaKey() {
        if (rsaKey == null) {
            rsaJwk();
        }
        return rsaKey;
    }

    public void reNewKeyPairModel(){
        try {
            AuthKey authKey = UserDb.getAuthKeys().stream().filter(key -> key.getKeyStatus().equals(AuthKeyStatus.ACTIVE)).findFirst().orElse(
                    new AuthKey()
            );

            authKey.setKeyStatus(AuthKeyStatus.INACTIVE);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            String keyId = UUID.randomUUID().toString();
            System.out.println("new KeyId: " + keyId);

            String encodedPublicKey = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(publicKey.getEncoded());
            String encodedPrivateKey = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(privateKey.getEncoded());

            UserDb.addAuthKey(new AuthKey(keyId, encodedPrivateKey, encodedPublicKey, AuthKeyStatus.ACTIVE));
            rsaJwk();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
