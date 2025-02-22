package kr.flooding.backend.domain.neis.model

import com.fasterxml.jackson.annotation.JsonProperty

class MealInfoRow(
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
	val loadDtm: String,
)
