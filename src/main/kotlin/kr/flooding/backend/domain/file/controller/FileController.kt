package kr.flooding.backend.domain.file.controller

import io.swagger.v3.oas.annotations.Operation
import kr.flooding.backend.domain.file.dto.response.UploadImageResponse
import kr.flooding.backend.domain.file.usecase.UploadImageUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("file")
class FileController(
	private val uploadImageUsecase: UploadImageUsecase,
) {
	@PostMapping("image")
	@Operation(summary = "이미지 업로드", description = "jpg, jpeg, jpg 확장자를 사용하는 이미지를 업로드합니다. (아직 확장자 제한 안함)")
	fun uploadImage(
		@RequestPart images: List<MultipartFile>,
	): ResponseEntity<UploadImageResponse> =
		uploadImageUsecase.execute(images).let {
			ResponseEntity.ok(it)
		}
}
