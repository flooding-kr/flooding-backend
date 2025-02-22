package kr.flooding.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FloodingApplication

fun main(args: Array<String>) {
	runApplication<FloodingApplication>(*args)
}
