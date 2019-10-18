package ru.fmtk.khlystov.culture_code.rest

import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.*
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO


@RepositoryRestController
@RequestMapping(value = "/recommendations")
class RecommendationController(private val recommendationService: RecommendationService)
    : ResourceProcessor<RepositoryLinksResource> {

    @GetMapping(value = "/userid/{userId}/itemtype/{itemType}/limit/{limit}")
    fun getRecommendationsForUserAndItemType(@PathVariable userId: String,
                                             @PathVariable itemType: ItemType,
                                             @PathVariable(required = false) limit: Short = 5)
            : RecommendationsDTO {
        return recommendationService.getRecommendations(userId, itemType, limit)
    }

    @PutMapping(value = "/userid/{userId}/itemtype/{itemType}/itemid/{itemId}")
    fun checkRecommendation(@PathVariable userId: String,
                            @PathVariable itemType: ItemType,
                            @PathVariable itemId: String) {
        recommendationService.checkRecommendations(
                RecommendationsDTO(userId, itemType, setOf(itemId))
        )
    }

    @PostMapping(value = "/compute")
    fun compute() {
        recommendationService.computeRecommendations()
    }

    override fun process(resource: RepositoryLinksResource): RepositoryLinksResource {
        resource.add(
                ControllerLinkBuilder.linkTo(RecommendationController::class.java)
                        .withRel("recommendations"))
        return resource;
    }
}