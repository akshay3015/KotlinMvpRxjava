package com.example.akshayshahane.kotlinmvpdemo.home

import com.google.gson.annotations.SerializedName


data class Response(

	@field:SerializedName("next")
	val next: Any? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)