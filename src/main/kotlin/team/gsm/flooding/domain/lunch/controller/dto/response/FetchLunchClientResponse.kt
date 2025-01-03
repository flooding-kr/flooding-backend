package team.gsm.flooding.domain.lunch.controller.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class FetchLunchClientResponse(
	@JsonProperty("mealServiceDietInfo")
	val mealServiceDietInfo: List<MealServiceDietInfo>?
) {
	class MealServiceDietInfo(
		val head: List<Head>?,
		val row: List<Row>?
	) {
		class Head(
			val listTotalCount: Int?,
			@JsonProperty("RESULT")
			val result: Result?
		) {
			class Result(
				@JsonProperty("CODE")
				val code: String,
				@JsonProperty("MESSAGE")
				val message: String
			)
		}

		class Row(
			@JsonProperty("ATPT_OFCDC_SC_CODE")
			val atptOfcdcScCode: String,
			@JsonProperty("ATPT_OFCDC_SC_NM")
			val atptOfcdcScNm: String,
			@JsonProperty("SD_SCHUL_CODE")
			val sdSchulCode: String,
			@JsonProperty("SCHUL_NM")
			val schulNm: String,
			@JsonProperty("MMEAL_SC_CODE")
			val mmealScCode: String,
			@JsonProperty("MMEAL_SC_NM")
			val mmealScNm: String,
			@JsonProperty("MLSV_YMD")
			val mlsvYmd: String,
			@JsonProperty("MLSV_FGR")
			val mlsvFgr: Double,
			@JsonProperty("DDISH_NM")
			val ddishNm: String,
			@JsonProperty("ORPLC_INFO")
			val orplcInfo: String,
			@JsonProperty("CAL_INFO")
			val calInfo: String,
			@JsonProperty("NTR_INFO")
			val ntrInfo: String,
			@JsonProperty("MLSV_FROM_YMD")
			val mlsvFromYmd: String,
			@JsonProperty("MLSV_TO_YMD")
			val mlsvToYmd: String,
			@JsonProperty("LOAD_DTM")
			val loadDtm: String
		)
	}
}
