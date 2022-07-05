package org.placeholder.validation

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(
    prefix = "validation.placeholders.config",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class PropertiesPlaceholderValidatorAutoConfiguration {

    @Bean
    fun propertiesPlaceholderValidatorEventListener(env: Environment) = PropertiesPlaceholderValidatorEventListener(env)
}