package com.example.bar_buddy.Activities;

import android.content.Context;
import android.net.Credentials;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doAnswer;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseAuth.class, FirebaseFirestore.class})
public class LoginActivityTest {
    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private FirebaseUser firebaseUser;

    @Mock
    private FirebaseFirestore firebaseFirestore;

    @Before
    public void before(){
        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firebaseFirestore);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
    }

    @Test
    public void getUserTest() {
        try{
            FirebaseUser u = new LoginActivity().getUser();
            assertNotNull(u);
            assertTrue(u instanceof FirebaseUser);
        } catch (Exception e){
            fail("Exception");
        }
    }

    @Test
    public void getDBTest(){
        try{
            FirebaseFirestore db = new LoginActivity().getDB();
            assertNotNull(db);
            assertTrue(db instanceof FirebaseFirestore);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    @Test
    public void getAuthTest(){
        try{
            FirebaseAuth auth = new LoginActivity().getAuth();
            assertNotNull(auth);
            assertTrue(auth instanceof FirebaseAuth);
        } catch (Exception e){
            fail("Exception");
        }
    }

}