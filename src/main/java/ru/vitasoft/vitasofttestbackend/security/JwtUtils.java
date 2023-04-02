package ru.vitasoft.vitasofttestbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.vitasoft.vitasofttestbackend.enums.Role;
import ru.vitasoft.vitasofttestbackend.services.UserService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;


@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private Environment environment;
    @Autowired
    private UserService userService;

    public String generateJwtToken(String login, String role) {
        Long userId = userService.getUserByLogin(login).getId();
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Integer.parseInt(environment.getRequiredProperty("jwt.time.expired")));
        System.out.println("secret: " + environment.getRequiredProperty("jwt.secret") + " expired: " + environment.getRequiredProperty("jwt.time.expired"));
        return JWT.create()
                .withSubject(login)
                .withClaim("userId", userId.toString())
                .withClaim("role", role)
                .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }


    // проверяет, не истек ли токен
    public Boolean validateJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        try {
            JWT.require(algorithm)
                    .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                    .acceptExpiresAt(Integer.parseInt(environment.getRequiredProperty("jwt.time.accept")))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            log.error("Exception {}", e.getMessage());
            e.printStackTrace(new PrintWriter(stringWriter));
            log.error("Exception {}", stringWriter);
            return false;
        }
        return true;
    }

    public Long getUserIdFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        return JWT.require(algorithm).build().verify(token).getClaim("userId").asLong();
    }

    public String getUserNameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        return JWT.require(algorithm).build().verify(token).getSubject();
    }

    public Role getRoleFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        return Role.valueOf(JWT.require(algorithm).build().verify(token).getClaim("role").asString());
    }

}
