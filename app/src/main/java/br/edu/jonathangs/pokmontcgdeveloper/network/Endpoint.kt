package br.edu.jonathangs.pokmontcgdeveloper.network

import br.edu.jonathangs.pokmontcgdeveloper.network.data.Error
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class Endpoint(private val service: WebService) {

    fun allSets() = safeCall { service.allSets() }

    private inline fun <T> safeCall(call: () -> Call<T>): RequestStatus<T> {
        return try {
            val response = call().execute()
            if (response.isSuccessful)
                RequestStatus.Success(response.body())
            else
                RequestStatus.Failure.Response(errorResponse(response))
        } catch (cause: Exception) {
            RequestStatus.Failure.Undefined(cause)
        }
    }

    private fun <T> errorResponse(response: Response<T>): Error? {
        return if (response.errorBody() == null)
            null
        else
            Gson().fromJson(response.errorBody()?.string(), Error::class.java)
    }

}