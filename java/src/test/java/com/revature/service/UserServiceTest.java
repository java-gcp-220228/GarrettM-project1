package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao mockDao;

    @InjectMocks
    private UserService systemUnderTest;

    @Test
    public void testLogin_positive() throws FailedLoginException, SQLException {
        UserDao mockUserDao = mock(UserDao.class);

        UserService systemUnderTest = new UserService(mockUserDao);

        when(mockUserDao.getUserByUsernameAndPassword(eq("test"), eq("test"))).thenReturn(new User(1000, "test", "test", "Employee"));

        User actual = systemUnderTest.login("test", "test");

        User expected = new User(1000, "test", "test", "Employee");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testLogin_negative() throws FailedLoginException, SQLException {
        UserDao mockUserDao = mock(UserDao.class);

        UserService systemUnderTest = new UserService(mockUserDao);

        when(mockUserDao.getUserByUsernameAndPassword(eq("1test"), eq("2test"))).thenReturn(new User(1000, "1test", "2test", "Employee"));

        User actual = systemUnderTest.login("1test", "2test");

        User expected = new User(1000, "test", "test", "Employee");

        Assertions.assertNotEquals(expected, actual);

    }

    @Test
    public void testLogin_exception() throws FailedLoginException, SQLException {
        UserDao mockUserDao = mock(UserDao.class);

        UserService systemUnderTest = new UserService(mockUserDao);

        when(mockUserDao.getUserByUsernameAndPassword(eq("1test"), eq("2test"))).thenReturn(null);


        User expected = new User(1000, "test", "test", "Employee");

        FailedLoginException thrown = Assertions.assertThrows(FailedLoginException.class, () -> {
            User actual = systemUnderTest.login("1test", "2test");
        });

        Assertions.assertEquals("Invalid username and/or password was provided", thrown.getMessage());

    }

    @Test
    public void testNoArgConstructor(){
        UserService mockService = new UserService();

        Assertions.assertNotNull(mockService);
    }

}
