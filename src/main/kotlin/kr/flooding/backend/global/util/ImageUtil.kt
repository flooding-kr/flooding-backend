package kr.flooding.backend.global.util

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ImageUtil {
	fun convertToWebp(
		fileName: String,
		multipartFile: MultipartFile,
	): ByteArray {
		try {
			val image = ImmutableImage.loader().fromStream(multipartFile.inputStream)
			return image.bytes(WebpWriter.DEFAULT)
		} catch (e: Exception) {
			throw HttpException(ExceptionEnum.FILE.FAILED_TO_CONVERT_WEBP.toPair())
		}
	}
}
