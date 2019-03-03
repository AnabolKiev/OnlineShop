package com.anabol.onlineshop.service.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.security.NoSuchAlgorithmException;

public class DefaultSecurityServiceTest {

    private DefaultSecurityService securityService;

    @Before
    public void before() {
        securityService = new DefaultSecurityService(null);
    }

    @Test
    public void testGetNextSalt() throws NoSuchAlgorithmException {
        byte[] salt = securityService.getNextSalt();
        assertEquals(16, salt.length);
    }

    @Test
    public void testGetHash() throws NoSuchAlgorithmException {
        // calculated with third-party online tool
        byte[] expectedHash = {0b01100011, 0b01000000, 0b00011000, 0b00110111, 0b00010100, 0b00110111, (byte) 0b10110010,
                (byte) 0b11001001, (byte) 0b11011111, 0b00110110, 0b00000110, (byte) 0b10001000, (byte) 0b11011101,
                (byte) 0b10010000, 0b01111000, 0b00001010, 0b01011111, (byte) 0b11001100, (byte) 0b10010010, (byte) 0b11000010};
        byte[] salt = "saltison".getBytes();
        byte[] hash = securityService.getHash("12345", salt);
        assertArrayEquals(expectedHash, hash);
    }
}
