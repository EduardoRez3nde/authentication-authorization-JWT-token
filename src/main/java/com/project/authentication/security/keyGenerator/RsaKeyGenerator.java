package com.project.authentication.security.keyGenerator;

import jakarta.annotation.PostConstruct;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaKeyGenerator {

    private final String keyDirectory = "/home/eduardo/Documents/workspace/authentication/keys";
    private final String publicKeyName = "publicKey.pem";
    private final String privateKeyName = "privateKey.pem";

    private final Path publicKeyPath = Paths.get(keyDirectory, publicKeyName);
    private final Path privateKeyPath = Paths.get(keyDirectory, privateKeyName);


    private RsaJsonWebKey rsa;

    public RsaKeyGenerator() { initialize(); }

    public void initialize() {
        try {
            Files.createDirectories(Paths.get(keyDirectory));
            boolean existKey = Files.exists(publicKeyPath) && Files.exists(privateKeyPath);

            if (existKey)
                loadKeys();
            else {
                generateKeys();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing keys");
        }
    }

    private void loadKeys() {

        try {
            final String publicKeyPem = new String(Files.readAllBytes(publicKeyPath));
            final String privateKeyPem = new String(Files.readAllBytes(privateKeyPath));

            final byte[] publicKey = Base64.getDecoder().decode(publicKeyPem
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "")
            );

            final byte[] privateKey = Base64.getDecoder().decode(privateKeyPem
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "")
            );

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

            rsa = new RsaJsonWebKey(rsaPublicKey);
            rsa.setPrivateKey(rsaPrivateKey);
            rsa.setKeyId("k1");

        } catch (Exception e) {
            throw new RuntimeException("error");
        }
    }

    private void generateKeys() {
        try {
            rsa = RsaJwkGenerator.generateJwk(2048);
            rsa.setKeyId("k1");

            RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();
            RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();

            Files.createFile(publicKeyPath);
            Files.createFile(privateKeyPath);

            saveKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()), publicKeyPath.toString(), false);
            saveKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()), privateKeyPath.toString(), true);

        } catch (JoseException e) {
            throw new RuntimeException("Error generating rsa keys");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveKey(String encodedKey, final String filename, final boolean isPrivate) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            out.write(isPrivate ? "-----BEGIN PRIVATE KEY-----\n" : "-----BEGIN PUBLIC KEY-----\n");
            out.write(encodedKey.replaceAll("(.{64})", "$1\n"));
            out.write(isPrivate ? "\n-----END PRIVATE KEY-----" : "\n-----END PUBLIC KEY-----");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error saving keys");
        }
    }

    public RSAPrivateKey getPrivateKey() {
        return rsa.getRsaPrivateKey();
    }

    public RSAPublicKey getPublicKey() {
        return rsa.getRsaPublicKey();
    }

    public String getKeyId() {
        return rsa.getKeyId();
    }
}
