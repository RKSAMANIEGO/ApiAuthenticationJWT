package com.example.demo.Jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utils {

	final String SECRET_KEY="enrikerodkelersamaniegoguzmanenrikerodkelersamaniegoguzman";
	
	Key secretKey=new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),SignatureAlgorithm.HS256.getJcaName());
	
	public String createToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date( System.currentTimeMillis() + (1000*60*15) ))
				.signWith(secretKey,SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();	
	}
	
	public boolean isTokenValid(String token, String username) {
		return username.equals(extractUsername(token))  && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		Date expiration= Jwts.parserBuilder()
		   .setSigningKey(secretKey)
		   .build()
		   .parseClaimsJws(token)
		   .getBody()
		   .getExpiration();
		
		return expiration.before(new Date());
	}
}
