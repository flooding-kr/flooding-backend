package com.example.demo.global.converter

import com.example.demo.domain.user.entity.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter
class StringListConverter : AttributeConverter<List<Role?>?, String?> {
	override fun convertToDatabaseColumn(attribute: List<Role?>?): String? {
		return attribute?.joinToString(",")
	}

	override fun convertToEntityAttribute(dbData: String?): List<Role>? {
		val roleList = dbData?.split(",")?.map { Role.valueOf(it) }?.toList()
		return roleList
	}
}
