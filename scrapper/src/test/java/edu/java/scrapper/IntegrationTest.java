package edu.java.scrapper;

import java.nio.file.Path;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.SneakyThrows;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        runMigrations(POSTGRES);
    }

    @SneakyThrows private static void runMigrations(JdbcDatabaseContainer<?> c) {
        Database database = DatabaseFactory.getInstance().
            findCorrectDatabaseImplementation(new JdbcConnection(c.createConnection("")));

        Liquibase liquibase = new liquibase.Liquibase(
            "master.xml",
            new DirectoryResourceAccessor(
                Path.of(".").toAbsolutePath().getParent().getParent().resolve("migrations")),
            database
        );

        new CommandScope("update")
            .addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, liquibase.getDatabase())
            .addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, liquibase.getDatabaseChangeLog())
            .execute();
    }

    @DynamicPropertySource static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
