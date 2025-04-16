package kr.flooding.backend.global.database.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
	@Bean
	fun connectionFactory(
		@Value("\${spring.data.redis.host}") host: String,
		@Value("\${spring.data.redis.port}") port: Int,
		@Value("\${spring.data.redis.password:}") password: String,
	): LettuceConnectionFactory {
		val config = RedisStandaloneConfiguration(host, port)
		if (password.isNotBlank()) config.setPassword(password)
		return LettuceConnectionFactory(config)
	}

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
