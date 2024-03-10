package com.example.employeeapplication;

import static org.mockito.Mockito.when;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MainLoginTest {

    private FirebaseAuth mockFirebaseAuth;

    private FirebaseUser mockFirebaseUser;

    private FirebaseDatabase mockFirebaseDatabase;

    private DatabaseReference mockDatabaseReference;

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = new MainActivity();
        mainActivity.firebaseAuth = mockFirebaseAuth;
        // Mocking FirebaseDatabase and DatabaseReference
        when(mockFirebaseDatabase.getReference("employees")).thenReturn(mockDatabaseReference);
        when(FirebaseDatabase.getInstance()).thenReturn(mockFirebaseDatabase);
    }

    @Test
    public void onCreate_userNotNull_fetchEmployeeData() {
        // Arrange
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);

        // Act
        mainActivity.onCreate(null);

        // Assert
        // Verify that the appropriate Firebase methods are called
        Mockito.verify(mockFirebaseAuth).getCurrentUser();
        Mockito.verify(mockDatabaseReference).child(Mockito.anyString());
    }

    @Test
    public void onCreate_userNull_showLoginToast() {
        // Arrange
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(null);

        // Mock Toast.makeText
        Toast mockToast = Mockito.mock(Toast.class);
        Mockito.doReturn(mockToast).when(mockToast).makeText(Mockito.any(), Mockito.any(), Mockito.anyInt());
        Mockito.doNothing().when(mockToast).show();

        // Act
        mainActivity.onCreate(null);

        // Assert
        // Verify that the appropriate toast message is shown
        Mockito.verify(mockToast).show();
    }
}
