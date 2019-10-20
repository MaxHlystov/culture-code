package ru.fmtk.khlystov.culture_code.rest

import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class RestResourcesAdditionalLinksConfig: ResourceProcessor<RepositoryLinksResource> {

    override fun process(resource: RepositoryLinksResource): RepositoryLinksResource {
        resource.add(
                linkTo(RecommendationController::class.java)
                        //.slash("auth/login")
                        .withRel("recommendations"))
        return resource
    }
}
