package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating

@RepositoryRestResource(path = "UserItemRating")
interface UserItemRatingRepository: MongoRepository<UserItemRating, String>