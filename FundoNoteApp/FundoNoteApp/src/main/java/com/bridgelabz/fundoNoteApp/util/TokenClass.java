package com.bridgelabz.fundoNoteApp.util;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public interface TokenClass {
	
	
	public static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	public static final byte[] secretBytes = secret.getEncoded();
	public static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

	
	public String jwtToken(int id);

	
	public int parseJWT(String jwt) ;

}
