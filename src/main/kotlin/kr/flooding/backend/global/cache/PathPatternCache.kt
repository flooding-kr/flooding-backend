package kr.flooding.backend.global.cache

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.springframework.web.util.pattern.PathPattern

@Component
class PathPatternCache(
    private val requestMappingHandlerMapping: RequestMappingHandlerMapping,
) {

    lateinit var pathPatterns: List<Pair<PathPattern, Set<String>>>
        protected set

    @EventListener(ApplicationReadyEvent::class)
    fun cachePatterns() {
        val methods = requestMappingHandlerMapping.handlerMethods

        pathPatterns = methods.flatMap { (mappingInfo, _) ->
            val method = mappingInfo.methodsCondition.methods.map { it.name }.toSet()
            mappingInfo.pathPatternsCondition?.patterns?.map { it to method } ?: emptyList()
        }
    }
}