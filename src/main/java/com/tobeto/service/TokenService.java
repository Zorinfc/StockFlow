package com.tobeto.service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Role;
import com.tobeto.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${SECRET_KEY}")
	private String KEY;

	public String createToken(User user) {
		JwtBuilder builder = Jwts.builder();

		Role rol = user.getRole();
		Map<String, Object> customKeys = new HashMap<String, Object>();
		customKeys.put("role", rol.getName());
		customKeys.put("email", user.getEmail());
		builder = builder.claims(customKeys);

//		Instant date = Instant.now().plus(15, ChronoUnit.MINUTES);
		Instant date = Instant.now().plus(1, ChronoUnit.HOURS);
//		Instant date = Instant.now().plus(20, ChronoUnit.SECONDS);

		builder = builder.id(user.getId().toString()).issuedAt(new java.util.Date()).expiration(Date.from(date));

		return builder.signWith(getKey()).compact();
	}

	public Claims tokenControl(String token) {
		JwtParser builder = Jwts.parser().verifyWith(getKey()).build();
		return builder.parseSignedClaims(token).getPayload();

	}

	private SecretKey getKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
		return key;
	}

// secret key uretme
//	
//	public static void main(String[] args) {
//		keyUret();
//	}
//
//	public static void keyUret() {
//		SecretKey key = Jwts.SIG.HS512.key().build();
//		String str = Encoders.BASE64.encode(key.getEncoded());
//		System.out.println(str);
//	}
}
