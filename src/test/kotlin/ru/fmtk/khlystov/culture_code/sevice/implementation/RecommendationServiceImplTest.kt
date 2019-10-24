package ru.fmtk.khlystov.culture_code.sevice.implementation

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean


@Import(value = [RecommendationServiceImpl::class])
@DisplayName("RecommendationService must ")
internal class RecommendationServiceImplTest {

    companion object {
        const val trustedUserId = "123456789"
        const val trustedItemId = "itemId_0001"
    }

    @Autowired
    lateinit var recommendationService: RecommendationServiceImpl

    lateinit var recommendationsRepository: RecommendationsRepository

//    @MockBean
//    lateinit var ratingService: RatingService
//
//    @MockBean
//    lateinit var usersService: UsersService

    @Test
    @DisplayName(" get recommendations for user id.")
    fun getRecommendations() {
        recommendationsRepository = mock(RecommendationsRepository::class.java)
        val ids = generateSequence(10) { i -> i + 1 }.take(4).map(Int::toString).toList()
        val recommendations = ids.map { id ->
            Recommendation(id, trustedUserId, ItemType.BOOK, "Item #$id")
        }.toList()
        val recommendationsDTO = RecommendationsDTO(trustedUserId, ItemType.BOOK, ids.toSet())
        given(recommendationsRepository.findAllByUserIdAndItemTypeAndCheckedFalse(eq(trustedUserId), eq(ItemType.BOOK), any()))
                .willReturn(recommendations)
        val result = recommendationService.getRecommendations(trustedUserId, ItemType.BOOK, 3)
        assertEquals(recommendationsDTO, result)
    }

    @Test
    @DisplayName(" compute recommendations for all users.")
    fun computeRecommendations() {
    }

    @Test
    @DisplayName(" get recommendations for user id")
    fun getUsersCloseness() {
    }

//    @TestConfiguration
//    internal class RecommendationServiceImplTestContextConfiguration {
//
//        @Bean
//        fun employeeService(): RecommendationServiceImpl {
//            return RecommendationServiceImpl()
//        }
//    }
}