/*
 * Copyright @today.year ViiSE.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.updater.util;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Реализация интерфейса {@link HashAlgorithms} по умолчанию.
 * <p>
 * Используемая кодировка - UTF8.
 * @author ViiSE
 * @since 1.0.0
 */
@Component("hashAlgorithmsDefault")
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
