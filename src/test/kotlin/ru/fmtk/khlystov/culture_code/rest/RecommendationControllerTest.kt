package ru.fmtk.khlystov.culture_code.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO


@Import(value = [RecommendationController::class])
@AutoConfigureMockMvc(secure = false)
@WebMvcTest(RecommendationController::class)
@DisplayName("RecommendationController must")
internal class RecommendationControllerTest {

    companion object {
        const val trustedUserId = "123456789"
        const val trustedItemId = "itemId_0001"
        val jsonMapper = jacksonObjectMapper()
    }

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var recommendationService: RecommendationService

    @DisplayName(" get recommendations for valid user id and item type")
    @Test
    fun getRecommendationsForUserAndItemType() {
        val items = generateSequence(10) { i -> i + 1 }
                .take(4).map(Int::toString).toSet()
        val recommendations = RecommendationsDTO(trustedUserId, ItemType.BOOK, items)
        given(recommendationService.getRecommendations(eq(trustedUserId), eq(ItemType.BOOK), eq(10)))
                .willReturn(recommendations)
        val jsonMatch = jsonMapper.writeValueAsString(recommendations)
        val mvcResult = mvc.perform(get("/recommendations/userid/$trustedUserId/itemtype/${ItemType.BOOK}/limit/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn()
        assertThat(jsonMatch)
                .isEqualToIgnoringWhitespace(mvcResult.response.contentAsString)
    }

    @DisplayName(" call service to set recommendation to checked state")
    @Test
    fun checkRecommendation() {
        mvc.perform(put("/recommendations/userid/$trustedUserId/itemtype/${ItemType.BOOK}/itemid/$trustedItemId"))
                .andExpect(status().isOk)

        val checkCaptor = argumentCaptor<RecommendationsDTO>()
        verify(recommendationService).checkRecommendations(checkCaptor.capture())
        val capturedRecommendation = checkCaptor.firstValue
        assertThat(capturedRecommendation.userId).isEqualTo(trustedUserId)
        assertThat(capturedRecommendation.itemType).isEqualTo(ItemType.BOOK)
        assertThat(capturedRecommendation.itemsId).isEqualTo(setOf(trustedItemId))
    }

    @DisplayName(" call service to compute recommendations")
    @Test
    fun compute() {
        mvc.perform(post("/recommendations/compute"))
                .andExpect(status().isOk)
        verify(recommendationService).computeRecommendations()
    }

    @Configuration
    class TestConfig
}