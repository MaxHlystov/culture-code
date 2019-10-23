package ru.fmtk.khlystov.culture_code.sevice.implementation

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.RatingService
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.UsersService

@Import(value = [RecommendationServiceImpl::class])
@SpringBootTest
@DisplayName("RecommendationService must ")
internal class RecommendationServiceImplTest {

    @MockBean
    lateinit var recommendationsRepository: RecommendationsRepository

    @MockBean
    lateinit var  ratingService: RatingService

    @MockBean
    lateinit var  usersService: UsersService

    @Test
    @DisplayName("get recommendations for valid user id and item type")
    fun getRecommendations() {
    }

    @Test
    @DisplayName("compute recommendations for all usres")
    fun computeRecommendations() {
    }

    @Test
    @DisplayName("get recommendations for valid user id and item type")
    fun checkRecommendations() {
    }
}