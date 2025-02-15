package team.gsm.flooding.global.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import team.gsm.flooding.domain.user.entity.Role

@Converter
class StringListConverter : AttributeConverter<List<Role?>?, String?> {
	override fun convertToDatabaseColumn(attribute: List<Role?>?): String? = attribute?.joinToString(",")

	override fun convertToEntityAttribute(dbData: String?): List<Role>? {
		val roleList = dbData?.split(",")?.map { Role.valueOf(it) }?.toList()
		return roleList
	}
}
