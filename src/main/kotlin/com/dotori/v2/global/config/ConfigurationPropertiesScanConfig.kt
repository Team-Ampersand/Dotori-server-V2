package com.dotori.v2.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.dotori.v2.global.config.gauth.properties", "com.dotori.v2.global.security.jwt.properties"])
class ConfigurationPropertiesScanConfig