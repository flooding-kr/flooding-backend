package kr.flooding.backend.global.thirdparty.youtube

import com.google.api.services.youtube.YouTube
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.youtube.dto.response.YoutubeInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class YoutubeAdapter(
    @Value("\${youtube-api.key}")
    private val apiKey: String,
    private val youTube: YouTube,
) {
    fun fetchYoutubeInfo(youtubeUrl: String): YoutubeInfoResponse {
        val youtubeId = extractYoutubeId(youtubeUrl)
        val video = youTube.videos()
            .list(listOf("statistics", "snippet"))
            .setKey(apiKey)
            .setId(listOf(youtubeId))
            .execute()
            .items.firstOrNull() ?: throw HttpException(ExceptionEnum.MUSIC.NOT_FOUND_MUSIC.toPair())

        return YoutubeInfoResponse(
            musicUrl = "https://www.youtube.com/watch?v=${video.id}",
            title = video.snippet.title,
            thumbnailImageUrl = "https://img.youtube.com/vi/${video.id}/0.jpg",
        )
    }

    private fun extractYoutubeId(url: String): String {
        val match = Regex("""^(https?:\/\/)?(www\.)?(youtube\.com|youtube-nocookie\.com|youtu\.be)\/(watch\?v=|embed\/|v\/)?([\w\-]+)(\S+)?$""")
            .matchEntire(url)
        return match?.groups?.get(5)?.value ?: throw HttpException(ExceptionEnum.MUSIC.INVALID_MUSIC_URL.toPair())
    }
}