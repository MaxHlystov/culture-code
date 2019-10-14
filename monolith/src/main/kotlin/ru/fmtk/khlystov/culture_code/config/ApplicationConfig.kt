package ru.fmtk.khlystov.culture_code.config

import com.github.cloudyrock.mongock.Mongock
import com.github.cloudyrock.mongock.SpringMongockBuilder
import com.mongodb.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    companion object {
        private val CHANGELOGS_PACKAGE = "ru.fmtk.khlystov.culture_code.changelogs"
    }

    @Bean
    fun mongock(mongoProps: MongoProps, mongoClient: MongoClient): Mongock {
        return SpringMongockBuilder(mongoClient, mongoProps.database, CHANGELOGS_PACKAGE)
                .setLockQuickConfig()
                .build()
    }


}