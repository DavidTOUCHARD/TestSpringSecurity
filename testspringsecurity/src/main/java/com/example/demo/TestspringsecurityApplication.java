package com.example.demo;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TestspringsecurityApplication {

	public static void main(String[] args) {

		passwordEncoderSimple();
		listProvider();
		listProviderServices();
		UtilisationProviderServices();
		listAlgo();
		calculerValeurDeHachage("MD5", "message");

	}

	public static void passwordEncoderSimple() {
		int i = 0;
		while (i < 1) {
			// mot de passe inserer par l'utilisateur lors de son inscription
			String password = "123456";

			// On chiffre le mot de passe
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);

			// On affiche le mot de passe chiffré
			System.out.println(hashedPassword);

			// Test ou l'utilisateur se connecte avec le bon mot de passe
			System.out.println(passwordEncoder.matches("123456", hashedPassword));

			// Test ou l'utilisateur se connecte avec le mauvais mot de passe
			System.out.println(passwordEncoder.matches("123457", hashedPassword));
			i++;
		}
	}

	public static void listProvider() {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			System.out.println("Provider : " + provider.getName() + " version " + provider.getVersionStr());
		}
	}

	public static void listProviderServices() {
		Provider provider = Security.getProvider("SunEC");
		System.out.println("\nServices du provider " + provider.getName());
		for (Service service : provider.getServices()) {
			System.out.println("Service \t" + service.getType() + " " + service.getAlgorithm());
		}
	}
	public static void listAlgo() {
	for (String algo : Security.getAlgorithms("Cipher")) {
	      System.out.println(algo);
	    }
	  }
	
	public static void UtilisationProviderServices() {
		Provider provider = Security.getProvider("SunEC");
		System.out.println("\nServices du provider " + provider.getName());
		Service service = provider.getService("KeyFactory", "XDH");
		KeyPairGenerator keyGen;
		try {
			
			keyGen = KeyPairGenerator.getInstance("XDH", "SunEC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(255, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();
			System.out.println("private key : " + priv.serialVersionUID);
			System.out.println("public key : " + pub.serialVersionUID);
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static byte[] calculerValeurDeHachage(String algorithme,
		      String monMessage) {
		    byte[] digest = null;
		    System.out.println("\n ");
		    try {
		      MessageDigest sha = MessageDigest.getInstance(algorithme);
		      sha.update(monMessage.getBytes());
		      digest = sha.digest();
		      System.out.println("algorithme : " + algorithme);
		     System.out.println(digest.toString());		 
		     //System.out.println(bytesToHex(digest));
		    } catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		    }
		    return digest;
		  }

}
