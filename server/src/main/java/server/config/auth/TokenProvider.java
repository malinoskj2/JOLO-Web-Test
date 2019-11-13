package server.config.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

@Component
public class TokenProvider implements Serializable {
    @Value("${jwt.ttl.seconds}")
    private long ttl;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsername(final String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(final String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(final String token,
                          final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaims(token));
    }

    private Claims getAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String generate(final UserDetails userDetails) {
        final AppUser appUser = (AppUser) userDetails;

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .claim("userID", appUser.getId())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ttl * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validate(final String token,
                            final UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
