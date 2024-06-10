package uz.pdp.back.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import uz.pdp.back.entity.Role;
import uz.pdp.back.entity.User;
import uz.pdp.back.exception.TokenExpiredException;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    public String generateToken(User user) {
        return "Bearer " + Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*30))
                .claim("roles", user.getRoles())
                .signWith(getPrivateKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return "Bearer " + Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*24*7))
                .claim("roles", user.getRoles())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        byte[] bytes = Decoders.BASE64.decode("a2345678a2345678a2345678a2345678a2345678a2345678a2345678a2345678");
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token is expired");
        }
    }

    public String getEmail(String token) {
        Jws<Claims> claimsJws = getClaims(token);
        return claimsJws.getBody().getSubject();
    }

    public List<Role> getRoles(String token) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jws<Claims> claimsJws = getClaims(token);
        String json = mapper.writeValueAsString(claimsJws.getBody().get("roles"));
        List<Role> roles = mapper.readValue(json, new TypeReference<List<Role>>() {});
        return roles;
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPrivateKey())
                .build()
                .parseClaimsJws(token);
    }
}
