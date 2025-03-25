package kr.flooding.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
class FloodingApplication

fun main(args: Array<String>) {
	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
	runApplication<FloodingApplication>(*args)
}
