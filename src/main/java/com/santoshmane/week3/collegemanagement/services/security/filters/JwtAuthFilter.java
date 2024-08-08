package com.santoshmane.week3.collegemanagement.services.security.filters;

import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import com.santoshmane.week3.collegemanagement.services.security.JwtService;
import com.santoshmane.week3.collegemanagement.services.security.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final String CLASS_NAME = "JwtAuthFilter";
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtService.validateToken(token)) {
                UserEntity userEntity = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            logRequest(request);
            filterChain.doFilter(request, response);
            logResponse(response);
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private void logRequest(HttpServletRequest request) {
        log.info("Incoming Request: {} {} from {} inside {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),CLASS_NAME);
    }

    private void logResponse(HttpServletResponse response) {
        log.info("Outgoing Response: status={} inside {}", response.getStatus(),CLASS_NAME );
    }
}
