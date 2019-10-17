package ru.fmtk.khlystov.culture_code.changelogs

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeLog(order = "003")
class InitTestMongoDBDataChangeLog {
    private val initTestMongoDBData = InitTestMongoDBData()

    @ChangeSet(order = "000", id = "initTestMongoDBData", author = "khlystov", runAlways = false)
    fun initTestMongoDBData(template: MongoTemplate) {
        initTestMongoDBData.run(template)
    }
}