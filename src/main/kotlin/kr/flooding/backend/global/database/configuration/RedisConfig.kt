package kr.flooding.backend.global.database.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
	@Bean
	fun connectionFactory(): LettuceConnectionFactory = LettuceConnectionFactory()

	@Bean
	fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, String> {
		val template = RedisTemplate<String, String>()
		template.connectionFactory = connectionFactory

		val serializer = StringRedisSerializer()
		template.keySerializer = serializer
		template.valueSerializer = serializer
		template.hashKeySerializer = serializer
		template.hashValueSerializer = serializer

		return template
	}
}
