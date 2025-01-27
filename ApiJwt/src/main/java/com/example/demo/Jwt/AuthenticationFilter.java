package com.example.demo.Jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter{

	private final Utils utils;
	private final UserDetailsService detailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String tokenHeader=request.getHeader("Authorization");
		
		String username=null;
		String token=null;
		
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			token=tokenHeader.substring(7);
			username=utils.extractUsername(token);
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			UserDetails detalleUsuario = detailsService.loadUserByUsername(username);
			
			if(utils.isTokenValid(token, username)) {
				
				UsernamePasswordAuthenticationToken auth= 
						new UsernamePasswordAuthenticationToken(detalleUsuario, null, detalleUsuario.getAuthorities());
				
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
}
