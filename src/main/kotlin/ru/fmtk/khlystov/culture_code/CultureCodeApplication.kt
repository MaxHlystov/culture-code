package ru.fmtk.khlystov.culture_code

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class CultureCodeApplication

fun main(args: Array<String>) {
	runApplication<CultureCodeApplication>(*args)
}
