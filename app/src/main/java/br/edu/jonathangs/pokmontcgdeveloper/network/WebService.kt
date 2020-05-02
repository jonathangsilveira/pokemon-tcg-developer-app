package br.edu.jonathangs.pokmontcgdeveloper.network

import br.edu.jonathangs.pokmontcgdeveloper.network.data.Cards
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Sets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("sets")
    fun allSets(): Call<Sets>

    @GET("cards")
    fun allCards(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int
    ): Call<Cards>

    @GET("cards")
    fun setCards(@Query("setCode") setCode: String): Call<Cards>

}