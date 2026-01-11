package com.example.project.saloon.gentlemanChair.security;

import com.example.project.saloon.gentlemanChair.entity.User;
import com.example.project.saloon.gentlemanChair.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestTokenHeader.substring(7);

        String userEmail = jwtUtil.getEmailFromToken(jwtToken);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = userRepository.findByEmail(userEmail).orElseThrow();

            UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request,response);

    }
}
