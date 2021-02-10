package hr.fer.oprpp1.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	private static String operation; 
	private static String operand1; 
	private static String operand2; 
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		operation = args[0]; 
		operand1 = args[1];
		
		if (args.length == 3) {
			operand2 = args[2]; 
		}
		
		switch (operation) {
			case "checksha": 
				doWork(operand1); 
				break; 
			case "encrypt":
			case "decrypt":
				doWork(operation, operand1, operand2);
				break; 
		}
		
		
	}
	
	private static void doWork(String operand1) throws NoSuchAlgorithmException {
		System.out.println("Please provide expected sha-256 digest for " + operand1 + ":");
		
		Scanner sc = new Scanner(System.in); 
		String expectedSha = sc.nextLine();
		
		byte[] result = null; 
		try (FileInputStream in = new FileInputStream(operand1);) {
			byte[] byteArray = new byte[4096]; 
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			int noOfBytes; 
			while ((noOfBytes = in.read(byteArray)) != -1) {
				md.update(byteArray, 0, noOfBytes);
			}
			result = md.digest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		 
		if (Util.byteToHex(result).equals(expectedSha)) {
			System.out.println("Digesting completed. Digest of " + operand1 + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + operand1 +
								" does not match the expected digest. Digest was: " + Util.byteToHex(result) + " or in byte: ");
		}
	}
	
	private static void doWork(String operation, String operand1, String operand2) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): " + operand2);
		
		Scanner sc = new Scanner(System.in);
		String hexPassword = sc.nextLine(); 
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits): ");
		String iVector = sc.nextLine(); 
		
		sc.close();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(hexPassword), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(iVector));
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(operation.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec); 
		
		try (FileInputStream in = new FileInputStream(operand1);
				FileOutputStream out = new FileOutputStream(operand2);) {
			byte[] byteArray = new byte[4096]; 
			int noOfBytes;
			while ((noOfBytes = in.read(byteArray)) > -1) {
				out.write(c.update(byteArray, 0, noOfBytes));
			}
			out.write(c.doFinal());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (operation.equals("encrypt")) {
			System.out.print("Encryption ");
		} else {
			System.out.print("Decryption ");
		}
		System.out.println("completed. Generated file " + operand2 + " based on file " + operand1);
		
	}

}
