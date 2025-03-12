package kr.flooding.backend.global.jackson.configuration

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {
	@Bean
	fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder =
		Jackson2ObjectMapperBuilder()
			.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}
