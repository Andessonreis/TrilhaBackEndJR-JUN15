package br.com.andesson.taskmanager.infrastructure.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.andesson.taskmanager.domain.user.model.User;

/**
 * Service class for generating JWT tokens for authenticated users.
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.expiration-hours}")
    private Long expirationHours;

    @Value("${api.security.token.timezone}")
    private String timeZone;

    private Algorithm algorithm;

    /**
     * Constructs a new TokenService with the specified secret key.
     *
     * @param secretKey the secret key for signing JWT tokens
     */
    public TokenService(@Value("${api.security.token.secret}") String secretKey) {
        this.secretKey = secretKey;
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param user the authenticated user
     * @return the generated JWT token
     */
    public String generateToken(User user) {
        try {
            Date issuedAt = new Date();
            Date expirationDate = generateExpirationDate();

            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("login-auth-api")
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);

            return token;
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Generates the expiration date for the JWT token based on the configured expiration hours and timezone.
     *
     * @return the expiration date
     */
    private Date generateExpirationDate() {
        return Date.from(LocalDateTime.now().plusHours(expirationHours).toInstant(ZoneOffset.of(timeZone)));
    }
}
