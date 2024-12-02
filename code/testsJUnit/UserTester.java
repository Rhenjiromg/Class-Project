package testsJUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import shared.User;

public class UserTester {
    private User defaultUser;
    private User ParametersUser;

    @BeforeEach
    @DisplayName(value = "Set-Up")
    void setUp() {
        //Initialize user with default values
        defaultUser = new User("Default", "Password");

        //Initialize user with parameters
        ArrayList<String> accs = new ArrayList<>();

        accs.add("A001");
        accs.add("A002");

        ParametersUser = new User("Tester", "U001", "Password", "User", accs);

    }

@Test
@DisplayName(value = "Test Constructors/Setters/Getters")
void testConstructorsAndGetters() {
    // assertEquals("Default", defaultUser.getName());
    // assertEquals("Password", defaultUser.getPassword());
    assertEquals("U001", defaultUser.getID());
    assertEquals(2, ParametersUser.getAccList().size());
    assertTrue(ParametersUser.getAccList().contains("A001"));
    assertTrue(ParametersUser.getAccList().contains("A002"));

    }

@Test
@DisplayName(value = "Test Add Account")
void testAddAccount() {
    defaultUser.addAccount("A003");
    defaultUser.addAccount("A004");

    assertEquals(2, defaultUser.getAccList().size());
    assertTrue(defaultUser.getAccList().contains("A003"));
    assertTrue(defaultUser.getAccList().contains("A004"));

    }

@Test
@DisplayName(value = "Test Authorization")
void testAuthorize() {
    assertTrue(ParametersUser.Authorize("A001"));
    assertTrue(ParametersUser.Authorize("A002"));
    assertTrue(ParametersUser.Authorize("A003"));

    }

@Test
@DisplayName(value = "Test File Prep")
void testFileProp() {
ArrayList<String> file = ParametersUser.filePrep();

assertEquals("Default", file.get(0));
assertEquals("U001", file.get(1));
assertEquals("Password", file.get(2));

assertTrue(file.contains("A001"));
assertTrue(file.contains("A002"));
    }

@Test
@DisplayName(value = "Test Get Info")
void testGetInfo() {
ArrayList<String> info = ParametersUser.getInfo();
assertEquals("Default", info.get(0));
assertEquals("U001", info.get(1));
assertEquals("Password", info.get(2));
assertTrue(info.contains("A001"));
assertTrue(info.contains("A002"));
    } 

@Test
@DisplayName(value = "Test Get Acc method")
void testGetAcc() {
    String[] accs = ParametersUser.getAcc();

    assertEquals(2, accs.length);
    assertEquals("A001", accs[0]);
    assertEquals("A002", accs[1]);
}
}
