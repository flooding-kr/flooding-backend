package kr.flooding.backend.global.database.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.flooding.backend.domain.user.enums.RoleType

@Converter
class StringListConverter : AttributeConverter<List<RoleType?>?, String?> {
	override fun convertToDatabaseColumn(attribute: List<RoleType?>?): String? =
		if (attribute.isNullOrEmpty()) {
			null
		} else {
			attribute.joinToString(",")
		}

	override fun convertToEntityAttribute(dbData: String?): List<RoleType>? {
		val roleList = dbData?.split(",")?.map { RoleType.valueOf(it) }?.toList()
		return roleList
	}
}
