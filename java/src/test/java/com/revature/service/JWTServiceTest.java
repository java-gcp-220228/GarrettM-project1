package com.revature.service;

import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class JWTServiceTest {

    @Test
    public void createJwtTest() {

        User mockUser = new User();
        JWTService jwtService = JWTService.getInstance();

        String actual = jwtService.createJWT(mockUser);

        Assertions.assertNotNull(actual);
    }

    @Test
    public void getJwtInstanceTest(){
        JWTService actual = JWTService.getInstance();

        Assertions.assertNotNull(actual);
    }

    @Test
    public void parseJwtTest_Positive(){
        User mockUser = new User();
        JWTService jwtService = JWTService.getInstance();
        String mockJWT = jwtService.createJWT(mockUser);

        Jws<Claims> actual = jwtService.parseJwt(mockJWT);

        Assertions.assertNotNull(actual);
    }

    @Test
    public void parseJwtTest_exception(){
        JWTService jwtService = JWTService.getInstance();

        UnauthorizedResponse thrown = Assertions.assertThrows(UnauthorizedResponse.class, () -> {
            Jws<Claims> actual = jwtService.parseJwt("mockJWT");
        });
        Assertions.assertEquals("JWT was invalid",thrown.getMessage());
    }


}

