package kr.flooding.backend.global.thirdparty.youtube.configuration

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class YoutubeConfig {
    @Bean
    fun youTubeConfig(): YouTube {
        return YouTube.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            null
        ).setApplicationName("music-server").build()
    }

}