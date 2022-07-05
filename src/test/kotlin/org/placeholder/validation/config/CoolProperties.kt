package org.placeholder.validation.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cool.properties")
class CoolProperties {
    lateinit var prop: String
    lateinit var anotherProp: String
}