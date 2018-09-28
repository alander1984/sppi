package com.egartech.sppi.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5PasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            String result = "";
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(charSequence.toString().getBytes(),0, charSequence.length());
                result = new BigInteger(1,messageDigest.digest()).toString(16);
                if (result.length() < 32) {
                    result = "0" + result;
                };
            }
            catch (NoSuchAlgorithmException e) {
                System.out.println(e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            String hash = "";
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(charSequence.toString().getBytes(),0, charSequence.length());
                hash = new BigInteger(1,messageDigest.digest()).toString(16);
                if (hash.length() < 32) {
                    hash = "0" + hash;
                };
            }
            catch (NoSuchAlgorithmException e) {
                System.out.println(e.getLocalizedMessage());
            }
            return StringUtils.equals(hash,s);
        }
}
