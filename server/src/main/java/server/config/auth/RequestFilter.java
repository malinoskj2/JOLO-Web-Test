package server.config.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import server.service.JwtUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class RequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<String> token = Optional.ofNullable(httpServletRequest.getHeader(AUTHORIZATION_HEADER))
                .flatMap(this::resolveToken);

        Optional<UserDetails> userDetails = token.map(this.tokenProvider::getUsername)
                .map(this.jwtUserDetailsService::loadUserByUsername);

        if (Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).isPresent()
                && token.isPresent()
                && userDetails.isPresent()
                && this.tokenProvider.validate(token.get(), userDetails.get())) {

            UsernamePasswordAuthenticationToken userPassAuthToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails.get(),
                            null,
                            userDetails.get().getAuthorities()
                    );

            userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<String> resolveToken(String tokenStr) {
        if (StringUtils.hasText(tokenStr) && tokenStr.startsWith("Bearer ")) {
            return Optional.of(tokenStr.substring(7));
        } else {
            return Optional.empty();
        }
    }
}
