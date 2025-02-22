package kr.flooding.backend.global.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.flooding.backend.domain.user.entity.Role

@Converter
class StringListConverter : AttributeConverter<List<Role?>?, String?> {
	override fun convertToDatabaseColumn(attribute: List<Role?>?): String? =
		if (attribute.isNullOrEmpty()) {
			null
		} else {
			attribute.joinToString(",")
		}

	override fun convertToEntityAttribute(dbData: String?): List<Role>? {
		val roleList = dbData?.split(",")?.map { Role.valueOf(it) }?.toList()
		return roleList
	}
}
