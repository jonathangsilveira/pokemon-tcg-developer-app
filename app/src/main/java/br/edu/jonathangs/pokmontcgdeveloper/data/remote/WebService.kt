package br.edu.jonathangs.pokmontcgdeveloper.data.remote

import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.SetsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("sets")
    suspend fun sets(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int
    ): Response<SetsResponse>

    @GET("sets")
    suspend fun allSets(): Response<SetsResponse>

    /*@GET("cards")
    fun cards(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int
    ): Call<CardsResponse>

    @GET("cards")
    fun cardsFrom(@Query("setCode") set: String): Call<CardsResponse>*/

}