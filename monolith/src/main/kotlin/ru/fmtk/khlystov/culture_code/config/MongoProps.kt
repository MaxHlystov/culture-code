package ru.fmtk.khlystov.culture_code.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.data.mongodb")
class MongoProps {
    var port: Int = 27017
    lateinit var host: String
    lateinit var database: String
    lateinit var uri: String
}