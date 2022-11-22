package com.example.baudot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTest {
    String cipherText1Test = "SVY9P KYOKH J";
    String cipherText2Test = "QSBIT FGMWB G";

    @Test
    void add() {
        assertEquals("L5N4I4K9MFZ", HelloApplication.add(cipherText1Test, cipherText2Test));
    }

    @Test
    void formatString() {
        assertEquals("SVY9PKYOKHJ", HelloApplication.formatString(cipherText1Test));
    }

    @Test
    void checkString() {
        assertEquals(18, HelloApplication.checkString(cipherText1Test, 0));
        assertEquals(10, HelloApplication.checkString(cipherText1Test, 9));
    }
}