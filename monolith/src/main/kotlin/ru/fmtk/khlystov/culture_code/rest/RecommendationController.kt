package ru.fmtk.khlystov.culture_code.rest

import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*
import ru.fmtk.khlystov.culture_code.model.ratings.ItemType
import ru.fmtk.khlystov.culture_code.sevice.RecommendationService
import ru.fmtk.khlystov.culture_code.sevice.dto.RecommendationsDTO


@RepositoryRestController
@RequestMapping(value = ["/recommendations"])
class RecommendationController(private val recommendationService: RecommendationService)
    : ResourceProcessor<Resource<RecommendationsDTO>> {

    @GetMapping(value = ["/userid/{userId}/itemtype/{itemType}/limit/{limit}"])
    fun getRecommendationsForUserAndItemType(@PathVariable userId: String,
                                             @PathVariable itemType: ItemType,
                                             @PathVariable(required = false) limit: Short = 5)
            : RecommendationsDTO {
        return recommendationService.getRecommendations(userId, itemType, limit)
    }

    @PutMapping(value = ["/userid/{userId}/itemtype/{itemType}/itemid/{itemId}"])
    fun checkRecommendation(@PathVariable userId: String,
                            @PathVariable itemType: ItemType,
                            @PathVariable itemId: String) {
        recommendationService.checkRecommendations(
                RecommendationsDTO(userId, itemType, setOf(itemId))
        )
    }

    @PostMapping(value = ["/compute"])
    fun compute() {
        recommendationService.computeRecommendations()
    }

    override fun process(resource: Resource<RecommendationsDTO>): Resource<RecommendationsDTO> {
        val query = resource.getContent()
        resource.add(linkTo(methodOn(RecommendationController::class.java)
                .getRecommendationsForUserAndItemType(query.userId, query.itemType, 5))
                .withRel("getrec"))
        return resource;
    }
}