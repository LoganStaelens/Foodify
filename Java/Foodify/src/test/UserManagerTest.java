package test;

import businessPackage.IUserManager;
import businessPackage.UserManager;
import exceptionPackage.HashException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private IUserManager userManager;

    @BeforeEach
    public void setup() {
        this.userManager = new UserManager();
    }

    @Test
    void hashPassword() throws HashException {
        assertEquals(64, this.userManager.hashPassword("w2eqeqweqwe").length());
        assertEquals(64, this.userManager.hashPassword("{}IQUWieuw675361787>/;'.}").length());
        assertEquals(64, this.userManager.hashPassword("KIKI").length());
    }

    @Test
    void verifyPassword() throws HashException {
        assertTrue(this.userManager.verifyPassword( this.userManager.hashPassword("w2eqeqweqwe"), this.userManager.hashPassword("w2eqeqweqwe")));
        assertTrue(this.userManager.verifyPassword( this.userManager.hashPassword("{}IQUWieuw675361787>/;'.}"), this.userManager.hashPassword("{}IQUWieuw675361787>/;'.}")));
        assertTrue(this.userManager.verifyPassword( this.userManager.hashPassword("KIKI"), this.userManager.hashPassword("KIKI")));
        assertTrue(this.userManager.verifyPassword( this.userManager.hashPassword("oeuiyhqwuiehqwknqw"), this.userManager.hashPassword("oeuiyhqwuiehqwknqw")));

        assertFalse(this.userManager.verifyPassword( this.userManager.hashPassword("weerwerwer"), this.userManager.hashPassword("oeuiyhqwuiehqwknqw")));
        assertFalse(this.userManager.verifyPassword( this.userManager.hashPassword("w2eqeqweqwe"), this.userManager.hashPassword("w2eqeqweqw")));
        assertFalse(this.userManager.verifyPassword( this.userManager.hashPassword("{}IQUWieuw675361787>/;'.}"), this.userManager.hashPassword("{}IQUWieuw65361787>/;'.}")));
    }
}