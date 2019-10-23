package ru.fmtk.khlystov.culture_code.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Configuration
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO

@AutoConfigureMockMvc(secure = false)
@WebMvcTest(RecommendationController::class)
@DisplayName("RecommendationController must ")
internal class RecommendationControllerTest {

    companion object {
        const val trustedUserId = "123456789"
        const val trustedUserIdWithoutRecommendations = "777777777777"
        const val notTrustedUserId = "0000000000"
        val jsonMapper = jacksonObjectMapper()
    }

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var recommendationService: RecommendationService

    @DisplayName("get recommendations for valid user id and item type")
    @WithMockUser(username = "Admin", password = "111111", roles = ["ADMIN"])
    @Test
    fun getRecommendationsForUserAndItemType() {
        val items = generateSequence(10) { i -> i + 1 }
                .take(4).map(Int::toString).toSet()
        val recommendations = RecommendationsDTO(trustedUserId, ItemType.BOOK, items)
        given(recommendationService.getRecommendations(trustedUserId, ItemType.BOOK, 10))
                .willReturn(recommendations)
        val jsonMatch = jsonMapper.writeValueAsString(recommendations)
        mvc.perform(get("/recommendations/userid/$trustedUserId/itemtype/${ItemType.BOOK}/limit/10"))
                //.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().json(jsonMatch))
    }

    @DisplayName(" set recommendation to checked state")
    @Test
    fun checkRecommendation() {
    }

    @Configuration
    class TestConfig {}
}