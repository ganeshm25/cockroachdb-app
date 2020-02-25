package com.corelogic.cockroachdb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.CockroachContainer;

import static java.lang.Boolean.parseBoolean;
import static org.springframework.test.context.support.TestPropertySourceUtils.addInlinedPropertiesToEnvironment;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(
    initializers = IntegrationTest.PropertyOverrideContextInitializer.class,
    classes = DemoApplication.class
)
public abstract class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    public static class PropertyOverrideContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        CockroachContainer container;

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            String useTestContainer = context.getEnvironment().getProperty("demo.test-container.enabled");
            if (parseBoolean(useTestContainer)) {
                if (container == null) {
                    container = new CockroachContainer();
                    container.start();
                }

                addInlinedPropertiesToEnvironment(context, "spring.datasource.url=" + container.getJdbcUrl());
                addInlinedPropertiesToEnvironment(context, "spring.datasource.username=" + container.getUsername());
                addInlinedPropertiesToEnvironment(context, "spring.datasource.password=" + container.getPassword());
            }
        }
    }
}
