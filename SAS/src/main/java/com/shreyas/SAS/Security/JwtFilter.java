package com.shreyas.SAS.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(1);
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;
        System.out.println(authHeader);
        if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
            token=authHeader.substring(7);
            System.out.println(token);
            username= jwtUtil.extractUsername(token);
            System.out.println(username);
        }
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
            System.out.println(username);
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if(jwtUtil.isTokenValid(token)) {
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("lets goo");
            }
        }
        filterChain.doFilter(request,response);
    }
}
