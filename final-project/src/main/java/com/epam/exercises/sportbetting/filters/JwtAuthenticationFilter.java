package com.epam.exercises.sportbetting.filters;

import com.epam.exercises.sportbetting.security.jwt.JwtSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String JWT_AUTH_HEADER_START = "Bearer";
    private static final int TOKEN_START_POSITION = 7;

    private UserDetailsService userDetailsService;
    private JwtSecurityService jwtSecurityService;

    @Autowired
    public void setUserDetailsService(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtSecurityService(JwtSecurityService jwtSecurityService) {
        this.jwtSecurityService = jwtSecurityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        if(validateJwt(jwt)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    getUserDetails(jwt), null, getUserDetails(jwt).getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateJwt(String jwt) {
        return StringUtils.hasText(jwt)
                && jwtSecurityService.validateJwt(getUserDetails(jwt).getUsername(), jwt)
                && !jwtSecurityService.isJwtInBlackList(jwt);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        return StringUtils.hasText(requestTokenHeader) && requestTokenHeader.startsWith(JWT_AUTH_HEADER_START) ?
                requestTokenHeader.substring(TOKEN_START_POSITION) : null;
    }

    private UserDetails getUserDetails(String jwt) {
        String userLogin = jwtSecurityService.getUserLoginFromJwt(jwt);
        return userDetailsService.loadUserByUsername(userLogin);
    }

}
