package com.bridgelabz.fundoNoteApp.user.service;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRep;
	String secretKey;

	@Override
	public String login(User user) {
		String password = securePassword(user);

		List<User> usrlst = userRep.findByIdAndPassword(user.getId(), password);
		System.out.println("SIZE : " + usrlst.size());

		if (usrlst.size() > 0 && usrlst != null) {
			System.out.println("Sucessful login");

			return "Welcome " + usrlst.get(0).getName() + "Jwt--->" + generateToken(usrlst.get(0).getId());

		} else {
			System.out.println("wrong emailid or password");
			return "wrong emailid or password";
		}
	}

	@Override
	public User userReg(User user) {
		user.setPassword(securePassword(user));
		userRep.save(user);
		return user;
	}

	@Override
	public User changeUserPassword(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(String token, User user) {
		int varifiedUserId = verifyToken(token);

		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		User presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setPhonenumber(
					user.getPhonenumber() != 0 ? user.getPhonenumber() : maybeUser.get().getPhonenumber());
			existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
			existingUser.setPassword(user.getPassword() != null ? securePassword(user) : maybeUser.get().getPassword());
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return userRep.save(presentUser);
	}

	@Override
	public boolean delete(String token) {
		int varifiedUserId = verifyToken(token);

		// return userRep.deleteById(varifiedUserId);
		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRep.delete(existingUser);
			return true;
		}).orElseGet(() -> false);

	}

	@Override
	public String securePassword(User user) {
		String password = user.getPassword();
		try {

			// Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// digest() method called
			// to calculate message digest of an input
			// and return array of byte
			// byte[] messageDigest = md.digest();

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, bytes);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			System.out.println(hashtext);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			System.out.println("Exception thrown" + " for incorrect algorithm: " + e);

			return null;
		}

	}

	@Override
	public User saveUser(User user) {

		User givenUser = new User();
		givenUser.setEmail(user.getEmail());
		givenUser.setName(user.getName());
		givenUser.setPhonenumber(user.getPhonenumber());
		givenUser.setPassword(securePassword(user));
		givenUser.setId(user.getId());
		userRep.save(givenUser);
		return givenUser;
	}

	// Token Generation
	@Override
	public String generateToken(int id) {

		Date now = new Date();
		Date exp = new Date(System.currentTimeMillis() + (1000 * 30)); // 30 seconds

		String token = Jwts.builder().setSubject(String.valueOf(id)).setIssuedAt(now).setNotBefore(now)
				.setExpiration(exp).signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();

		return token;
	}

	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	private static final byte[] secretBytes = secret.getEncoded();
	private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

	public int verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
		System.out.println("----------------------------");
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
		return Integer.parseInt(claims.getSubject());
	}
}