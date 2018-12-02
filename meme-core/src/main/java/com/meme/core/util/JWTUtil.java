package com.meme.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class JWTUtil {

	private static Key getKey(){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String jwtSecret=PropertiesUtil.getProperty("jwt_secret");
		byte[] jwtSecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
		Key signingKey = new SecretKeySpec(jwtSecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}
	
	public static String buildToken(Map<String, Object> claims){
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKey()).compact();
	}
	
	public static Map<String, Object> verifyToken(String jwt) {
		if(StringUtil.isEmpty(jwt)) return null;
        try {
        	JwtParser parser=Jwts.parser().setSigningKey(getKey());
        	Jws<Claims> jws=parser.parseClaimsJws(jwt);
        	if(null!=jws) return jws.getBody();
        	return null;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
}
