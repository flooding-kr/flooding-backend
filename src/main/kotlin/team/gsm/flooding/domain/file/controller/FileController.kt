package team.gsm.flooding.domain.file.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import team.gsm.flooding.domain.file.dto.response.UploadImageResponse
import team.gsm.flooding.domain.file.usecase.UploadImageUsecase

@RestController
@RequestMapping("file")
class FileController(
	private val uploadImageUsecase: UploadImageUsecase,
) {
	@PostMapping("image")
	fun uploadImage(
		@RequestPart images: List<MultipartFile>,
	): ResponseEntity<UploadImageResponse> =
		uploadImageUsecase.execute(images).let {
			ResponseEntity.ok(it)
		}
}
