package br.edu.jonathangs.pokmontcgdeveloper.data.remote

import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.CardsResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.SubtypesResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.SupertypesResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.TypesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CardsWebService {

    @GET("cards")
    suspend fun cards(@QueryMap parameters: Map<String, String>): Response<CardsResponse>

    /*@GET("cards")
    fun cards(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int
    ): Call<CardsResponse>*/

    @GET("types")
    suspend fun types(): Response<TypesResponse>

    @GET("supertypes")
    suspend fun supertypes(): Response<SupertypesResponse>

    @GET("subtypes")
    suspend fun subtypes(): Response<SubtypesResponse>

}