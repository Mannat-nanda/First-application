package com.example.mannatsandroidlab;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.mannatsandroidlab.MainActivity;

public class MainActivityTest {

    private final MainActivity mainActivity = new MainActivity();

    @Test
    public void testCheckPasswordComplexity_WithValidPassword() {
        assertTrue(mainActivity.checkPasswordComplexity("Valid1$Password"));
    }

    @Test
    public void testCheckPasswordComplexity_WithoutUpperCase() {
        assertFalse(mainActivity.checkPasswordComplexity("invalid1$"));
    }

    @Test
    public void testCheckPasswordComplexity_WithoutLowerCase() {
        assertFalse(mainActivity.checkPasswordComplexity("INVALID1$"));
    }

    @Test
    public void testCheckPasswordComplexity_WithoutNumber() {
        assertFalse(mainActivity.checkPasswordComplexity("InvalidPassword$"));
    }

    @Test
    public void testCheckPasswordComplexity_WithoutSpecialCharacter() {
        assertFalse(mainActivity.checkPasswordComplexity("Invalid1Password"));
    }

    @Test
    public void testIsSpecialCharacter_WithSpecialCharacter() {
        assertTrue(mainActivity.isSpecialCharacter('$'));
    }

    @Test
    public void testIsSpecialCharacter_WithoutSpecialCharacter() {
        assertFalse(mainActivity.isSpecialCharacter('A'));
    }
}