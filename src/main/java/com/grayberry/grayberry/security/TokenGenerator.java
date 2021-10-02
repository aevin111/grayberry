package com.grayberry.grayberry.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator
{
    public String generateToken()
    {
        SecureRandom random = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[255];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
