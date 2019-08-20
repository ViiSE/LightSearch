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

/**
 * Константы для реализаций {@link lightsearch.server.security.HashAlgorithms}.
 * @author ViiSE
 */
public enum HashAlgorithmsEnum {
    SHA256 {
        @Override
        public String stringValue() { return "SHA-256"; }
    },
    UTF8 {
        @Override
        public String stringValue() { return "UTF-8"; }
    };
    
    public abstract String stringValue();
}
