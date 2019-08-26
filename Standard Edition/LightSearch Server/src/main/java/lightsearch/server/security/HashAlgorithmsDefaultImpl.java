/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Реализация интерфейса {@link lightsearch.server.security.HashAlgorithms} по умолчанию.
 * <p>
 * Используемая кодировка - UTF8.
 * @author ViiSE
 * @since 1.0.0
 */
public class HashAlgorithmsDefaultImpl implements HashAlgorithms {

    private final String SHA256 = HashAlgorithmsEnum.SHA256.stringValue();
    private final String UTF8 = HashAlgorithmsEnum.UTF8.stringValue();
    
    @Override
    public String sha256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA256);
            byte[] hash = digest.digest(message.getBytes(UTF8));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } 
        catch(UnsupportedEncodingException | NoSuchAlgorithmException ignore){
           return null;
        }
    }
}
