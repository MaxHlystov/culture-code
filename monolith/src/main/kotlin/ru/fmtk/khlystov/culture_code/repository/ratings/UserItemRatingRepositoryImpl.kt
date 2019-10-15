package ru.fmtk.khlystov.culture_code.repository.ratings

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import java.util.*


open class UserItemRatingRepositoryImpl(private val mongoTemplate: MongoTemplate) : UserItemRatingRepositoryCustom {
    override fun save(userItemRating: UserItemRating): Optional<UserItemRating> {
        val query = Query.query(Criteria().andOperator(
                Criteria.where("userId").`is`(ObjectId(userItemRating.userId)),
                Criteria.where("itemType").`is`(userItemRating.itemType),
                Criteria.where("itemId").`is`(ObjectId(userItemRating.itemId))))
        val update = Update()
        update.set("rating", userItemRating.rating)
        return Optional.ofNullable(mongoTemplate.findAndModify(query, update,
                FindAndModifyOptions().returnNew(true),
                UserItemRating::class.java))
    }
}