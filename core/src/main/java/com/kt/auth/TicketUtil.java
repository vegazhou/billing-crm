package com.kt.auth;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.kt.util.EncryptUtil;

public final class TicketUtil {

	private static Logger logger =Logger.getLogger(TicketUtil.class.getName());

	private static int IV_OFFSET = 4;
	private static int IVBYTES_LENGTH = 16;
	private static int DATA_OFFSET = 20;
	public static final String SEPARATOR = ";";
	public static final String EQUALSIGN = "=";
	private static String AESKEY = "abe6b953f9505858ee8c50759ade41bd";
	private final static String SECRETKEY_ALGORITHM = "AES";
	private TicketUtil() {
	}


	private static SecretKey getSecuritKey() throws Exception {
		byte[] keyByte = null;
		keyByte = EncryptUtil.sha2sum(AESKEY.getBytes());

		return new SecretKeySpec(keyByte,SECRETKEY_ALGORITHM);
	}

	/**
	 * To generate a ticket string representing the current user info
	 * Ticket = Modified Base64 (16bytes IV ||Ek ( SHA256 ( M )  || M ) )
	 */
	public static String generateTicket(final SessionTicket ticket) throws Exception {

		try {
			byte[] rawTicket = ticket.getRawTicket();
			//SHA256(M)
			byte[] hashed = EncryptUtil.sha2sum(rawTicket);
			//SHA256(M)||M
			int hashlength = hashed.length;
			int rawlength = rawTicket.length;
			byte[] newTicket = new byte[hashlength + rawlength];
			System.arraycopy(hashed, 0, newTicket, 0, hashlength);
			System.arraycopy(rawTicket, 0, newTicket, hashlength, rawlength);
			//Ek(SHA256(M)||M)
			SecretKey lastKey = getSecuritKey();
			byte[] ivByte = generate16Bytes();
			byte[] bytes = EncryptUtil.encrypt(newTicket,lastKey,ivByte);
			//Final ByteArray
			byte[] newbytes = new byte[DATA_OFFSET + bytes.length];
			System.arraycopy(bytes, 0, newbytes, DATA_OFFSET, bytes.length);
			System.arraycopy(ivByte, 0, newbytes, IV_OFFSET, IVBYTES_LENGTH);
			String ticketstr = new String(Base64.encodeBase64URLSafe(newbytes));
			return ticketstr;

		} catch (Exception e) {
			throw new Exception("unable to encrypt", e);
		}
	}

	private static String getByte(byte [] b){
		if(b==null) return "";
		String result = "";
		for(int i=0;i<b.length;i++){
			result = result + b[i] +",";
		}
		return result;
	}
	/**
	 * decode a ticket string to a Ticket object
	 * ticket format = userId%appName%createTime%timeToLive
	 * Ticket = Modified Base64 (Signature(2 bytes) || key version(2 bytes) ||16bytes IV ||Ek ( SHA256 ( M )  || M ) )
	 * key version is used to decrypt
	 *
	 * @param ticket
	 * @return Ticket
	 */
	public static SessionTicket getTicket(final String ticket,final boolean checkExpired) throws Exception{
		byte[] ivByte = null;
		SecretKey key = null;
		try {
			//byte[] bytes = Base64Util.base64ToByteArray(ticket);
			byte[] bytes = Base64.decodeBase64(ticket.getBytes());
			ivByte = new byte[IVBYTES_LENGTH];
			System.arraycopy(bytes, IV_OFFSET, ivByte,0, IVBYTES_LENGTH);

			key = getSecuritKey();

			//decode algorithm
			byte[] newTicket = EncryptUtil.decrypt(bytes, DATA_OFFSET, bytes.length - DATA_OFFSET, key,ivByte);

			//Get rawTicket
			int rawlength = newTicket.length - EncryptUtil.SHA2_LENGTH;
			byte[] rawTicket = new byte[rawlength];
			System.arraycopy(newTicket, EncryptUtil.SHA2_LENGTH, rawTicket, 0, rawlength);
			new String(rawTicket);
			//Get hash in ticket
			byte[] hashed = new byte[EncryptUtil.SHA2_LENGTH];
			System.arraycopy(newTicket, 0, hashed, 0, EncryptUtil.SHA2_LENGTH);

			//Generate new Hashed
			byte[] newHashed = EncryptUtil.sha2sum(rawTicket);

			//Check hash
			if (!MessageDigest.isEqual(hashed, newHashed)){
				logger.error("unable to decrypt, ticket has been changed for ticket: " + ticket);
				throw new Exception("unable to decrypt, ticket has been changed");
			}
			SessionTicket rticket = new SessionTicket();
			rticket.setRawTicket(rawTicket);

			if(checkExpired){
				if(rticket.isExpired()){
					throw new Exception("Ticket is expired.");
				}
			}
			return rticket;
		} catch (Exception e) {
			logger.error("Decrypt ticket: " + ticket);
			logger.error("Decrypt IV is " + getByte(ivByte));
			logger.error("Decrypt Encrypt Key is " + getByte(key.getEncoded()));
			throw new Exception("Unable to Decrypt", e);
		}
	}

	/**
	 * update a ticket time stamp
	 *
	 * @param ticket
	 * @param ticketType
	 * @param change
	 * @return Ticket
	 */
	public static String updateTicket(final String ticketString)
		throws Exception{
		SessionTicket ticket = (SessionTicket)getTicket(ticketString);
		if(ticket.getTimeToLive()>0){
			ticket.resetStartTime(System.currentTimeMillis());
		}
		return generateTicket(ticket);
	}

	/**
	/**
	 * verify the ticket itself not the wrapper
	 *
	 * @param ticket
	 * @param ticketType
	 * @return boolean true is OK
	 */

	public static boolean verifyTicket(final String ticket, int ticketType)
		throws Exception {
		try {
			SessionTicket tk = getTicket(ticket);

			if (tk.getTimeToLive() == 0L) {
				return true;
			} else {
				return !tk.isExpired();
			}

		} catch	 (Exception e) {
			logger.error("unable to decrypt the ticket", e);
			throw new Exception("unable to decrypt the ticket", e);
		}
	}



	public static boolean validateTicket(final String ticket, int ticketType)
		throws Exception {
		SessionTicket tk = getTicket(ticket);
		return true;
	}
	/**
	 * Generate random 16-length byte array
	 * @return
	 */
	private static byte[] generate16Bytes(){
		SecureRandom random = new SecureRandom();
		byte [] randomBytes = new byte[16];
		random.nextBytes(randomBytes);
		return randomBytes;
	}


	public static SessionTicket getTicket(final String ticket) throws Exception {
		return getTicket(ticket,false);
	}


}
