package com.jonichi.soc.config;

import com.jonichi.soc.models.Account;
import com.jonichi.soc.models.User;
import com.jonichi.soc.repositories.AccountRepository;
import com.jonichi.soc.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken implements Serializable {

    // Taken from application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository repository;

    private static final long serialVersionUID = -4246326795551877445L;

    // Validity = 5hrs
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // generateToken and doGenerateToken methods are working together to build a single JWT token.
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        // .setClaims includes the information to show the recipient which is the username
        // .setSubject adds information about the subject
        // .setIssuedAt sets the time and date when the token was created
        // .setExpiration sets the expiration of the token
        // .signWith creates the token using a declared algorithm, with the secret keyword
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        User user = repository.findByUsername(userDetails.getUsername()).orElseThrow();
        claims.put("user", user.getId());
        return doGenerateToken(claims, userDetails.getUsername());

    }

    // Checks if the token is valid
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        String claim = getClaimFromToken(token, Claims::getSubject);
        return claim;
    }

    /*
        These two functions, getAllClaimsFromToken and getClaimFromToken, are typically used as utility methods,
        they may not be exposed for external use in most cases. Instead, they are used internally by other methods
        that need to extract information
    */
    // gets a specific property from token using GetAllClaims
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // gets all properties from token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}

