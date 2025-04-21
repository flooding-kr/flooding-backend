package kr.flooding.backend

import kr.flooding.backend.global.properties.CorsProperties
import kr.flooding.backend.global.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.TimeZone

@EnableRetry
@EnableScheduling
@EnableConfigurationProperties(CorsProperties::class, JwtProperties::class)
@SpringBootApplication
class FloodingApplication

fun main(args: Array<String>) {
	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	runApplication<FloodingApplication>(*args)
}
