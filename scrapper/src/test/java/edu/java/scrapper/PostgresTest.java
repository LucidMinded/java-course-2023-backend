package edu.java.scrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostgresTest extends IntegrationTest {
    @Test @SneakyThrows void testLinkTable() {
        Connection connection = POSTGRES.createConnection("");
        ResultSet result = connection.createStatement().executeQuery("SELECT * FROM link");
        ResultSetMetaData metaData = result.getMetaData();
        Assertions.assertEquals("id", metaData.getColumnName(1));
        Assertions.assertEquals("url", metaData.getColumnName(2));
        Assertions.assertEquals("updated_at", metaData.getColumnName(3));
    }

    @Test @SneakyThrows void testChatTable() {
        Connection connection = POSTGRES.createConnection("");
        ResultSet result = connection.createStatement().executeQuery("SELECT * FROM chat");
        ResultSetMetaData metaData = result.getMetaData();
        Assertions.assertEquals("id", metaData.getColumnName(1));
    }

    @Test @SneakyThrows void testChatLinkTable() {
        Connection connection = POSTGRES.createConnection("");
        ResultSet result = connection.createStatement().executeQuery("SELECT * FROM chat_link");
        ResultSetMetaData metaData = result.getMetaData();
        Assertions.assertEquals("chat_id", metaData.getColumnName(1));
        Assertions.assertEquals("link_id", metaData.getColumnName(2));
    }
}
