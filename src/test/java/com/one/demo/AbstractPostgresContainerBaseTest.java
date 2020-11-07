package com.one.demo;

import io.micronaut.context.env.Environment;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.Nonnull;
import java.util.Map;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest(transactional = false, environments = {Environment.TEST})
public abstract class AbstractPostgresContainerBaseTest implements AbstractBaseTest {
	@Container
	static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
		new PostgreSQLContainer<>(DockerImageName.parse("postgres"))
			.withDatabaseName("db_test")
			.withUsername("postgres")
			.withPassword("password");

	@Nonnull
	@Override
	public Map<String, String> getProperties() {
		return Map.of(
			"datasources.default.jdbc-url",POSTGRES_CONTAINER.getJdbcUrl(),
			"datasources.default.username",POSTGRES_CONTAINER.getUsername(),
			"datasources.default.password",POSTGRES_CONTAINER.getPassword(),
			"datasources.default.schema-generate", "NONE",
			"datasources.default.dialect",  "POSTGRES",
			"flyway.datasources.default.locations",  "classpath:db-migrations",
			"flyway.datasources.default.enabled",  "true");
	}
}
