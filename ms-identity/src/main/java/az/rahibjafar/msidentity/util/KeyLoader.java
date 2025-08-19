package az.rahibjafar.msidentity.util;

import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyLoader {

    private static byte[] readKeyBytes(String path) throws Exception {
        try (InputStream is = new ClassPathResource(path).getInputStream()) {
            String key = new String(is.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");
            return Base64.getDecoder().decode(key);
        }
    }

    public static KeyPair loadKeyPair(String publicPath, String privatePath) throws Exception {
        byte[] pubBytes = readKeyBytes(publicPath);
        byte[] privBytes = readKeyBytes(privatePath);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pubBytes));
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privBytes));

        return new KeyPair(publicKey, privateKey);
    }
}