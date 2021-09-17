package main.test;

import LoginSignUp.LoginController;
import LoginSignUp.SignUpController2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * <h2>Unit Test | Functions</h2>
 * <p>Unit tests for Functions</p>
 *
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 *
 */
public class UnitTest_FUNCTIONS {
    LoginController loginController;
    SignUpController2 signUpController2;

    /**
     * <h2>Setting Up</h2>
     * <p>Initializing objects before running the tests</p>
     */
    @Before
    public void init(){
        signUpController2 = new SignUpController2();
        loginController = new LoginController();
    }

    /**
     * <h2>Password Strength Check</h2>
     * <p>This checks if a password has number and character in it.</p>
     */
    @Test
    public void signUpController2_passwordStrengthChecker(){

        //given
        String password_with_number_and_character = "password123";

        //test
        assertTrue(signUpController2.checkPasswordStrength(password_with_number_and_character));
    }
    /**
     * <h2>Gmail Domain Check</h2>
     * <p>This checks if  provided domain is gmail or not.</p>
     */
    @Test
    public void loginController_isThisGmailChecker(){

        //given
        String a_gmail_domain = "test@gmail.com";

        //test
        assertTrue(loginController.checkGmail(a_gmail_domain));
    }
}
