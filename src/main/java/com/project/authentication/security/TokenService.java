package com.project.authentication.security;


import com.project.authentication.entities.User;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    private final static String ISSUER = "authentication.service";
    private final static String AUDIENCE = "my-client";

    private final RsaJsonWebKey rsa;

    public TokenService() {
        this.rsa = generateRsaKey();
    }

    private RsaJsonWebKey generateRsaKey() {
        try {
            RsaJsonWebKey keyPair = RsaJwkGenerator.generateJwk(2048);
            keyPair.setKeyId("k1");
            return keyPair;
        } catch(JoseException e) {
            throw new RuntimeException("Erro ao gerar a chave RSA", e);
        }
    }

    private JwtClaims createClaims(User user) {
        final JwtClaims claims = new JwtClaims();
        claims.setIssuer(ISSUER);
        claims.setAudience(AUDIENCE);
        claims.setExpirationTimeMinutesInTheFuture(60);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(1);
        claims.setSubject(user.getId().toString());
        claims.setClaim("email", user.getUsername());
        claims.setStringListClaim("authorities",
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
        return claims;
    }

    public String generateToken(User user)   {

        try {
            final JsonWebSignature jws = new JsonWebSignature();
            JwtClaims claims = createClaims(user);

            jws.setPayload(claims.toJson());
            jws.setKey(rsa.getPrivateKey());
            jws.setKeyIdHeaderValue(rsa.getKeyId());
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

            return jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException("Error generate token");
        }
    }

    public JwtClaims validateToken(String token) throws JoseException, InvalidJwtException {

        final JwtConsumer jwt = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer(ISSUER)
                .setExpectedAudience(AUDIENCE)
                .setVerificationKey(rsa.getPublicKey())
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();

        return jwt.processToClaims(token);
    }
}
