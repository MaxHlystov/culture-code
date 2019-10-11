package ru.fmtk.khlystov.culture_code.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.data.mongodb")
data class MongoProps(private val port: Int,
                               private val database: String,
                               private val uri: String)