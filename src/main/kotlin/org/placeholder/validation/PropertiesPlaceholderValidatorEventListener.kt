package org.placeholder.validation

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.EnumerablePropertySource
import org.springframework.core.env.Environment

class PropertiesPlaceholderValidatorEventListener(private val env: Environment) :
    ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val properties = HashSet<String>()

        if (env is ConfigurableEnvironment) {
            for (propertySource in env.propertySources) {
                if (propertySource is EnumerablePropertySource<*>) {
                    for (key in propertySource.propertyNames) {
                        properties.add(key)
                    }
                }
            }
        }

        val unresolvedProperties = HashMap<String, String>()
        for (property in properties) {
            try {
                env.getProperty(property)
            } catch (e: Exception) {
                unresolvedProperties[property] = e.message ?: "No message"
            }
        }

        if (unresolvedProperties.isNotEmpty()) {
            StringBuilder().apply {
                unresolvedProperties.forEach { (key, value) ->
                    appendLine("$value. Property key = $key")
                }
            }.let {
                throw IllegalStateException("Unresolved properties:\n$it")
            }
        }
    }
}
