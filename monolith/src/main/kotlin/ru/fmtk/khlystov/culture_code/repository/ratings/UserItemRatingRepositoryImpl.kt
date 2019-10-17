package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import ru.fmtk.khlystov.culture_code.model.ratings.ItemAvgRating
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import ru.fmtk.khlystov.culture_code.model.recomendations.TwoUsersCloseness
import java.util.*


open class UserItemRatingRepositoryImpl(private val mongoTemplate: MongoTemplate) : UserItemRatingRepositoryCustom {
    override fun save(userItemRating: UserItemRating): Optional<UserItemRating> {
        val query = Query.query(Criteria().andOperator(
                Criteria.where("userId").`is`(userItemRating.userId),
                Criteria.where("itemType").`is`(userItemRating.itemType),
                Criteria.where("itemId").`is`(userItemRating.itemId)))
        val update = Update()
        update.set("rating", userItemRating.rating)
        return Optional.ofNullable(mongoTemplate.findAndModify(query, update,
                FindAndModifyOptions().returnNew(true),
                UserItemRating::class.java))
    }

    override fun getAVGRatingsForItemType(itemType: ItemType): List<ItemAvgRating> {
        val aggregation = newAggregation(UserItemRating::class.java,
                match(Criteria.where("ItemType").`is`(itemType)
                        .and("rating").gt(0.0)),
                group("itemType", "itemId").avg("rating").`as`("avgRating"),
                project("avgRating").and("_id.itemType").`as`("itemType")
                        .and("_id.itemId").`as`("itemId"),
                sort(Sort.Direction.DESC, "avgRating")
        )
        val groupResults = mongoTemplate.aggregate(aggregation, ItemAvgRating::class.java)
        return groupResults.mappedResults
    }

    override fun getClosenessByRating(userId: String): List<TwoUsersCloseness> {
        val aggregation = newAggregation(UserItemRating::class.java,
                match(Criteria.where("userId").`is`(userId)
                        .and("rating").gt(0.0)),
                lookup("Ratings_UserItemRating", "itemId", "itemId", "items"),
                unwind("\$items"),
                project().andExclude("_id")
                        .andExpression("userId < items.userId").`as`("mid")
                        .andExpression("rating * items.rating").`as`("mul")
                        .and("userId").`as`("first")
                        .and("items.userId").`as`("second"),
                match(Criteria.where("mid").`is`(true)),
                group("first", "second").sum("mul").`as`("closeness"),
                match(Criteria.where("closeness").gt(0)),
                project("closeness").and("_id.first").`as`("firstUserId")
                        .and("_id.second").`as`("secondUserId"),
                sort(Sort.Direction.DESC, "closeness")
        )
        val groupResults = mongoTemplate.aggregate(aggregation, TwoUsersCloseness::class.java)
        return groupResults.mappedResults
    }
}