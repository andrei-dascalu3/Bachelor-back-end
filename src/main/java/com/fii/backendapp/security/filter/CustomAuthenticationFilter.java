package com.fii.backendapp.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fii.backendapp.domain.Role;
import com.fii.backendapp.util.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final static String SECRET = "20b5abe39a95bd4d69a1e6999514c56690c50da23b04850e1c8b79a25352caea";
    private final static Integer EXP_TIME = 2 * 60 * 60 * 1000;
    private final static Integer REFRESH_EXP_TIME = 24 * 60 * 60 * 1000;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username: {}\nPassword: {}", username, password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        // This is UserDetails User class
        CustomUser user = (CustomUser) authentication.getPrincipal();
        // Key is to be properly obtained in the future
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        // calculating ms until expiration of token
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + EXP_TIME);
        // access jwt token
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expirationDate)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        // refresh jwt token
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXP_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        // time until token expires
        Long expiresIn = expirationDate.getTime() - now.getTime();
        // user id
        Long uid = user.getUid();
        // roles
        Collection<GrantedAuthority> roles = user.getAuthorities();
        // tokens in response
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("expiresIn", expiresIn.toString());
        tokens.put("uid", uid.toString());
        tokens.put("isProfessor", user.isProfessor().toString());
        tokens.put("roles", getRolesAsString(roles));
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private static String getRolesAsString(Collection<GrantedAuthority> roles) {
        StringBuilder builder = new StringBuilder("[");
        for (var role : roles) {
            builder.append("\"").append(role.toString()).append("\"").append(", ");
        }
        return new StringBuilder(builder.substring(0, builder.length() - 2)).append("]").toString();
    }
}
