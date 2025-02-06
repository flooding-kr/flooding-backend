package team.gsm.flooding.domain.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("health")
    fun getHealth(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello world!")
    }
}