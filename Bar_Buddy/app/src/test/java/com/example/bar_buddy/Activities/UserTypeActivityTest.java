package com.example.bar_buddy.Activities;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseAuth.class, FirebaseFirestore.class})
public class UserTypeActivityTest {
    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private FirebaseUser firebaseUser;

    @Mock
    private FirebaseFirestore firebaseFirestore;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private Task mockTask;

    @Before
    public void before(){
        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firebaseFirestore);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
    }

    @Test
    public void newManagerTest() {
        when(firebaseFirestore.collection(anyString())).thenReturn(collectionReference);
        when(collectionReference.document(anyString()).set(anyMap())).thenReturn(mockTask);


    }

    @Test
    public void newCustomerTest() {
    }
}