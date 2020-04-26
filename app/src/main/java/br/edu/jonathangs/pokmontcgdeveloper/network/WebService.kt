package br.edu.jonathangs.pokmontcgdeveloper.network

import br.edu.jonathangs.pokmontcgdeveloper.network.data.Sets
import retrofit2.Call
import retrofit2.http.GET

interface WebService {

    @GET("sets")
    fun allSets(): Call<Sets>

}