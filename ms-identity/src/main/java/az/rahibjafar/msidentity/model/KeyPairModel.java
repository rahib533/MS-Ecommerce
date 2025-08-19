package az.rahibjafar.msidentity.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import com.nimbusds.jose.jwk.RSAKey;

public class KeyPairModel {
    private String kId;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private RSAKey rsaKey;

    public KeyPairModel(String kId, PrivateKey privateKey, PublicKey publicKey) {
        this.kId = kId;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
    public String getkId() {
        return kId;
    }
    public void setkId(String kId){
        this.kId = kId;
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
    public RSAKey getRsaKey() {
        return rsaKey;
    }
    public void setRsaKey(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }
}
