package com.revature.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtilityTest {

    @Test
    public void getConnection_Test() throws SQLException {
        Connection actual = ConnectionUtility.getConnection();

        Assertions.assertNotNull(actual);
    }
}
