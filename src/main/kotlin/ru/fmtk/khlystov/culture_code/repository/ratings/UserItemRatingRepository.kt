package ru.fmtk.khlystov.culture_code.repository.ratings

import org.springframework.data.mongodb.repository.MongoRepository
import ru.fmtk.khlystov.culture_code.model.ratings.UserItemRating

interface UserItemRatingRepository: MongoRepository<UserItemRating, String> {
}