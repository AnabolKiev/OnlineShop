package com.anabol.onlineshop.service.impl;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class DefaultSecurityServiceTest {

    @Test
    public void testGetNextSalt() throws NoSuchAlgorithmException {
        DefaultSecurityService securityService = new DefaultSecurityService();
        System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(securityService.getNextSalt()));
    }

    @Test
    public void testGetHash() throws NoSuchAlgorithmException {
        DefaultSecurityService securityService = new DefaultSecurityService();
        byte[] salt = "UgqCz8+Gl7lr5MqZcmhyFQ==".getBytes();
        byte[] hash = securityService.getHash("12345", salt);
        System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(hash));
    }
}
