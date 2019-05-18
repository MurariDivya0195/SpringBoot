package com.bridgelabz.fundoNoteApp.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;
import com.bridgelabz.fundoNoteApp.util.TokenClass;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRep;
	String secretKey;
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private TokenClass tokennum;

	@Override
	public String login(User user) {

		List<User> usrlst = userRep.findByIdAndPassword(user.getId(), securePassword(user.getPassword()));

		if (usrlst.size() > 0 && usrlst != null) {
			return "Welcome " + usrlst.get(0).getName() + "Jwt--->" + tokennum.jwtToken(usrlst.get(0).getId());
		} else {
			return "wrong emailid or password";
		}
	}

	@Override
	public User userReg(User user) {
		user.setPassword(securePassword(user.getPassword()));
		userRep.save(user);
		return user;
	}

	@Override
	public User update(String token, User user) {
		int varifiedUserId = tokennum.parseJWT(token);

		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		User presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setPhonenumber(
					user.getPhonenumber() != null ? user.getPhonenumber() : maybeUser.get().getPhonenumber());
			existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
			existingUser.setPassword(user.getPassword() != null ? securePassword(user.getPassword()) : maybeUser.get().getPassword());
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return userRep.save(presentUser);
	}

	@Override
	public boolean delete(String token) {
		int varifiedUserId = tokennum.parseJWT(token);

		// return userRep.deleteById(varifiedUserId);
		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRep.delete(existingUser);
			return true;
		}).orElseGet(() -> false);

	}

	@Override
	public String securePassword(String password) {
		
		String securepassword="";
		try {

			// Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i]&0xff)+0x100,16).substring(1));
			}
			securepassword=sb.toString();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return securepassword;
			
	}

	@Override
	public User saveUser(User user) {

		user.setPassword(securePassword(user.getPassword()));
		userRep.save(user);
		return user;
	}

	// Token Generation
	/*
	 * @Override public String generateToken(int id) {
	 * 
	 * Date now = new Date(); Date exp = new Date(System.currentTimeMillis() + (1000
	 * * 3000)); // 30 seconds
	 * 
	 * String token =
	 * Jwts.builder().setSubject(String.valueOf(id)).setIssuedAt(now).setNotBefore(
	 * now) .setExpiration(exp).signWith(SignatureAlgorithm.HS256,
	 * base64SecretBytes).compact();
	 * 
	 * return token; }
	 */
	/*
	 * private static final Key secret =
	 * MacProvider.generateKey(SignatureAlgorithm.HS256); private static final
	 * byte[] secretBytes = secret.getEncoded(); private static final String
	 * base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
	 * 
	 * public int verifyToken(String token) { Claims claims =
	 * Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody(
	 * ); System.out.println("----------------------------");
	 * System.out.println("ID: " + claims.getId()); System.out.println("Subject: " +
	 * claims.getSubject()); System.out.println("Issuer: " + claims.getIssuer());
	 * System.out.println("Expiration: " + claims.getExpiration()); return
	 * Integer.parseInt(claims.getSubject()); }
	 */

	@Override
	public User getUserInfoByEmail(String email) {
		return userRep.findByEmail(email);

	}

	@Override
	public Optional<User> findById(int id) {
		return userRep.findById(id);

	}

	@Override
	public String sendmail(String subject, User userdetails, String appUrl) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {

			helper.setTo(userdetails.getEmail());
			helper.setText("To reset your password, click the link below:\n" + appUrl);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

}