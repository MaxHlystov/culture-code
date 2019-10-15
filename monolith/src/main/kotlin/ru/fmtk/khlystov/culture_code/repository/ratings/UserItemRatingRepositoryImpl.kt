package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import java.util.*


open class UserItemRatingRepositoryImpl(private val mongoTemplate: MongoTemplate) : UserItemRatingRepositoryCustom {
    override fun save(userItemRating: UserItemRating): Optional<UserItemRating> {
        val query = Query.query(Criteria().andOperator(
                Criteria.where("userId").`is`(userItemRating.userId), //ObjectId(userItemRating.userId)),
                Criteria.where("itemType").`is`(userItemRating.itemType),
                Criteria.where("itemId").`is`(userItemRating.itemId))) //ObjectId(userItemRating.itemId))))
        val update = Update()
        update.set("rating", userItemRating.rating)
        return Optional.ofNullable(mongoTemplate.findAndModify(query, update,
                FindAndModifyOptions().returnNew(true),
                UserItemRating::class.java))
    }

    override fun getAVGRatings(): List<ItemAvgRating> {
        val aggregation = newAggregation(UserItemRating::class.java,
                group("itemType", "itemId").avg("rating").`as`("avgRating"),
                project("avgRating").and("itemType").previousOperation()
                        .and("itemId").previousOperation(),
                sort(Sort.Direction.DESC, "avgRating")
        )
        val groupResults = mongoTemplate.aggregate(aggregation, ItemAvgRating::class.java)
        return groupResults.mappedResults

    }
}