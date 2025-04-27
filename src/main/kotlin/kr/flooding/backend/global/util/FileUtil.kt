package kr.flooding.backend.global.util

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.properties.AwsProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.time.Duration

@Component
class FileUtil {
	companion object {
		private val imageExtensions = setOf("png", "jpg", "jpeg", "gif")

		fun MultipartFile.isImageExtension(): Boolean {
			val extension = this.originalFilename?.split(".")?.last()
			return extension in imageExtensions
		}
	}

	fun convertToWebp(
		fileName: String,
		multipartFile: MultipartFile,
	): ByteArray {
		try {
			val image = ImmutableImage.loader().fromStream(multipartFile.inputStream)
			return image.bytes(WebpWriter.DEFAULT.withMultiThread())
		} catch (e: Exception) {
			throw HttpException(ExceptionEnum.FILE.FAILED_TO_CONVERT_WEBP.toPair())
		}
	}
}
