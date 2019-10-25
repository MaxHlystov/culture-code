package ru.fmtk.khlystov.culture_code.sevice.implementation

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Import
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.model.recomendations.Recommendation
import ru.fmtk.khlystov.culture_code.repository.recommendations.RecommendationsRepository
import ru.fmtk.khlystov.culture_code.sevice.RatingService
import ru.fmtk.khlystov.culture_code.sevice.UsersService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO


@Import(value = [RecommendationServiceImpl::class])
@ExtendWith(MockitoExtension::class)
@DisplayName("RecommendationService must ")
internal class RecommendationServiceImplTest {

    companion object {
        const val trustedUserId = "123456789"
        const val trustedItemId = "itemId_0001"
    }

    @InjectMocks
    lateinit var recommendationService: RecommendationServiceImpl

    @Mock
    lateinit var recommendationsRepository: RecommendationsRepository

    @Mock
    lateinit var ratingService: RatingService

    @Mock
    lateinit var usersService: UsersService

    @Test
    @DisplayName(" get recommendations for user id.")
    fun getRecommendations() {
        val ids = generateSequence(10) { i -> i + 1 }.take(4)
                .map { id -> "Item #$id" }.toList()
        val recommendations = ids.map { id ->
            Recommendation(id, trustedUserId, ItemType.BOOK, id)
        }.toList()
        val recommendationsDTO = RecommendationsDTO(trustedUserId, ItemType.BOOK, ids.toSet())
        given(recommendationsRepository.findAllByUserIdAndItemTypeAndCheckedFalse(eq(trustedUserId), eq(ItemType.BOOK), any()))
                .willReturn(recommendations)
        val result = recommendationService.getRecommendations(trustedUserId, ItemType.BOOK, 3)
        assertEquals(recommendationsDTO, result)
    }

    @Test
    @DisplayName(" check recommendations for user id")
    fun checkRecommendations() {
        val ids = generateSequence(10) { i -> i + 1 }.take(4)
                .map { id -> "Item #$id" }.toList()
        val recommendations = ids.map { id ->
            Recommendation(id, trustedUserId, ItemType.BOOK, id)
        }.toList()
        val recommendationsDTO = RecommendationsDTO(trustedUserId, ItemType.BOOK, ids.toSet())
        recommendationService.checkRecommendations(recommendationsDTO)
        val checkedRecommendations = argumentCaptor<Collection<Recommendation>>()
        Mockito.verify(recommendationsRepository).setRecommendationsAsChecked(checkedRecommendations.capture())
        val capturedRecommendation = checkedRecommendations.firstValue
        val checkedItemsId = capturedRecommendation.filter(Recommendation::checked)
                .map(Recommendation::itemId).toList()
        assertIterableEquals(checkedItemsId, recommendationsDTO.itemsId)
    }
}