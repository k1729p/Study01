package kp.company.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import kp.company.model.Department;
import kp.company.model.Employee;
import kp.company.model.Title;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.UnaryOperator;

/**
 * The configuration for Open API.
 */
@Configuration
public class OpenApiConfiguration {

    private static final boolean SHORT_INFO = true;

    private static final String TITLE = "Study01 API";
    private static final String DESCRIPTION = "The sample application";
    private static final String CONTACT_NAME = "API Support";
    private static final String CONTACT_URL = "#";
    private static final String CONTACT_EMAIL = "support@example.com";
    private static final String LICENSE_NAME = "Apache 2.0";
    private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";
    private static final String TERMS_OF_SERVICE = "#";
    private static final String EXT_DOC_DESC = "Portfolio";
    private static final String EXT_DOC_URL = "https://github.com/ee-eng-cs/Portfolio";

    private static final String DEPARTMENTS_GROUP = "Departments";
    private static final String EMPLOYEES_GROUP = "Employees";
    private static final String TITLES_GROUP = "Titles";
    private static final UnaryOperator<String> PATTERN_FUN = "/%s/**"::formatted;
    private static final String DEPARTMENTS_PATH_PATTERN = PATTERN_FUN.apply("departments");
    private static final String EMPLOYEES_PATH_PATTERN = PATTERN_FUN.apply("employees");
    private static final String TITLES_PATH_PATTERN = PATTERN_FUN.apply("titles");

    /**
     * Creates the {@link OpenAPI}
     *
     * @param version the API version
     * @return the {@link OpenAPI}
     */
    @Bean
    public OpenAPI createOpenAPI(@Value("${springdoc.version}") String version) {

        if (SHORT_INFO) {
            return new OpenAPI().info(new Info().title(TITLE).version(version));
        }
        final Contact contact = new Contact().name(CONTACT_NAME).url(CONTACT_URL).email(CONTACT_EMAIL);
        final License license = new License().name(LICENSE_NAME).url(LICENSE_URL);
        final Info info = new Info().title(TITLE).version(version).description(DESCRIPTION).contact(contact)
                .license(license).termsOfService(TERMS_OF_SERVICE);
        final ExternalDocumentation documentation = new ExternalDocumentation().description(EXT_DOC_DESC)
                .url(EXT_DOC_URL);
        final Components components = new Components();
        return new OpenAPI().info(info).externalDocs(documentation).components(components);
    }

    /**
     * Creates the {@link GroupedOpenApi} for the {@link Department}s.
     *
     * @return the {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi createGroupedOpenApiForDepartments() {
        return GroupedOpenApi.builder().group(DEPARTMENTS_GROUP).pathsToMatch(DEPARTMENTS_PATH_PATTERN).build();
    }

    /**
     * Creates the {@link GroupedOpenApi} for the {@link Employee}s.
     *
     * @return the {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi createGroupedOpenApiForEmployees() {
        return GroupedOpenApi.builder().group(EMPLOYEES_GROUP).pathsToMatch(EMPLOYEES_PATH_PATTERN).build();
    }

    /**
     * Creates the {@link GroupedOpenApi} for the {@link Title}s.
     *
     * @return the {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi createGroupedOpenApiForTitles() {
        return GroupedOpenApi.builder().group(TITLES_GROUP).pathsToMatch(TITLES_PATH_PATTERN).build();
    }

}
