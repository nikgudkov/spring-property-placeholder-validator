package org.placeholder.validation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.placeholder.validation.config.CoolProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

class AppTest {

    @SpringBootApplication
    @ConfigurationPropertiesScan(basePackageClasses = [CoolProperties::class])
    class SpringBootApp

    @Test
    @Throws(IllegalStateException::class)
    fun `Context loads with no property placeholders validation`() {
        val args = arrayOf("--validation.placeholders.config.enabled=false")
        val app = SpringApplication(SpringBootApp::class.java).run(*args)

        val props = app.getBean(CoolProperties::class.java)

        assertThat(props.prop).isEqualTo("${'$'}{TEST_PLACEHOLDER_PROP}")
        assertThat(props.anotherProp).isEqualTo("${'$'}{TEST_PLACEHOLDER_ANOTHER_PROP}")
    }

    @Test
    @Throws(IllegalStateException::class)
    fun `Exception thrown during context initialisation with unresolved property placeholders`() {
        val springApplication = SpringApplication(SpringBootApp::class.java)
        assertThatThrownBy { springApplication.run() }.isInstanceOf(IllegalStateException::class.java).hasMessage(
            """Unresolved properties:
Could not resolve placeholder 'TEST_PLACEHOLDER_PROP' in value "${'$'}{TEST_PLACEHOLDER_PROP}". Property key = cool.properties.prop
Could not resolve placeholder 'TEST_PLACEHOLDER_ANOTHER_PROP' in value "${'$'}{TEST_PLACEHOLDER_ANOTHER_PROP}". Property key = cool.properties.another_prop
"""
        )
    }

}
