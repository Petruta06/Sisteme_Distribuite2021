package org.examen

import io.micronaut.core.annotation.*

@Introspected
class Consumer {
	private lateinit var news : List<String>

	fun setNews(news : List<String>){this.news = news}
	fun getNews() : List<String> = this.news
}


