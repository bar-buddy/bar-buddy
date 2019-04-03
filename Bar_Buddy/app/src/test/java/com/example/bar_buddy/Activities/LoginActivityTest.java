package com.example.bar_buddy.Activities;

import android.content.Context;
import android.net.Credentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class LoginActivityTest {
    private FirebaseApp mFirebaseApp;
    @Mock private Credentials mCredentials;
    @Mock private FirebaseAuth mFirebaseAuth;

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString(){
        LoginActivity myObjectUnderTest = new LoginActivity();
        assertEquals(4, 2 + 2);
    }

}