package ru.polskiy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.UserService;

import javax.crypto.SecretKey;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    /**
     * Generates a JWT for the given login.
     *
     * @param userDetails the login for which to generate the JWT
     * @return the generated JWT
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(signKey())
                .compact();
    }

    /**
     * Extracts the login from the given JWT.
     *
     * @param token the JWT from which to extract the login
     * @return the extracted login
     */
    public String extractLogin(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts all claims from the given JWT.
     *
     * @param token the JWT from which to extract the claims
     * @return the extracted claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the roles from the given JWT token.
     *
     * This method extracts all claims from the provided JWT token and retrieves the "roles" claim,
     * which is expected to be a list of strings. If the "roles" claim is not present or is not of
     * the expected type, this method may throw an exception.
     *
     * @param token the JWT token from which to extract the roles.
     * @return a list of roles extracted from the token.
     */
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    /**
     * Generates the secret key for signing JWT.
     *
     * @return the generated secret key
     */
    private SecretKey signKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
