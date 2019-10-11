package ru.fmtk.khlystov.culture_code.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.data.mongodb")
data class MongoProps(val port: Int,
                      val host: String,
                      val database: String,
                      val uri: String)