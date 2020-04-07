package br.com.bidei

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BideiBackendApplication

fun main(args: Array<String>) {
	runApplication<BideiBackendApplication>(*args)
}
