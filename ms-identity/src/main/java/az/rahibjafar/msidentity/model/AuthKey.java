package az.rahibjafar.msidentity.model;

import com.nimbusds.jose.jwk.RSAKey;

public class AuthKey {
    private String kId;
    private String privateKey;
    private String publicKey;
    private AuthKeyStatus keyStatus;
    private RSAKey rsaKey;

    public AuthKey() {
    }

    public AuthKey(String kId, String privateKey, String publicKey, AuthKeyStatus keyStatus) {
        this.kId = kId;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.keyStatus = keyStatus;
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public AuthKeyStatus getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(AuthKeyStatus keyStatus) {
        this.keyStatus = keyStatus;
    }
    public RSAKey getRsaKey() {
        return rsaKey;
    }
    public void setRsaKey(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }
}
