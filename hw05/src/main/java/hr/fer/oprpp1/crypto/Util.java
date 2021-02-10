package hr.fer.oprpp1.crypto;

public class Util {

	public static byte[] hexToByte(String keyText) {
		if (keyText.length() % 2 != 0) throw new IllegalArgumentException("Size of keyText must be even.");
		
		byte[] byteArray = new byte[keyText.length() / 2]; 
		for (int i = 0; i < keyText.length(); i += 2) {
			byteArray[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
									+ Character.digit(keyText.charAt(i+1), 16)); 
					
		}
		
		return byteArray; 
	}
	
	public static String byteToHex(byte[] byteArray) {
		if (byteArray.length == 0) return new String(); 
		
		char[] temp = new char[byteArray.length * 2];
		
		for (int i = 0; i < byteArray.length; i++) {
			temp[i * 2] = Character.forDigit(byteArray[i] >> 4 & 0xF, 16);
			temp[i * 2 + 1] = Character.forDigit(byteArray[i] & 0xF, 16);
		}
		return String.valueOf(temp); 
	}
	
}
