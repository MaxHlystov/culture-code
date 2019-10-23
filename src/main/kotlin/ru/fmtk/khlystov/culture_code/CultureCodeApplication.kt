package ru.fmtk.khlystov.culture_code

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import ru.fmtk.khlystov.culture_code.security.SecurityAppProperties

@SpringBootApplication
@EnableConfigurationProperties(SecurityAppProperties::class)
@EnableWebMvc
@EnableMongoRepositories
class CultureCodeApplication

fun main(args: Array<String>) {
    runApplication<CultureCodeApplication>(*args)
}
