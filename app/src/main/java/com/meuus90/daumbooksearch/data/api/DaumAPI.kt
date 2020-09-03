package com.meuus90.daumbooksearch.data.api

import com.meuus90.daumbooksearch.data.model.book.BookResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DaumAPI {
    @GET("v3/search/book")
    suspend fun getBookListSus(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("target") target: String?,
        @Query("size") size: Int?,
        @Query("page") page: Int?
    ): BookResponseModel
}