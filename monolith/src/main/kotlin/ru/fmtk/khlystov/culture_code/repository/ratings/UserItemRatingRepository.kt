package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating
import java.util.*

@RepositoryRestResource(path = "UserItemRating")
interface UserItemRatingRepository : PagingAndSortingRepository<UserItemRating, String>, UserItemRatingRepositoryCustom {
    @Query("{ 'userId' : ?0, 'itemType' : ?1, 'rating' : { 'gt' : 0 } }")
    fun getUserRatingsForItemType(userId: String, itemType: ItemType): Set<UserItemRating>
}

interface UserItemRatingRepositoryCustom {
    fun save(userItemRating: UserItemRating): Optional<UserItemRating>
}