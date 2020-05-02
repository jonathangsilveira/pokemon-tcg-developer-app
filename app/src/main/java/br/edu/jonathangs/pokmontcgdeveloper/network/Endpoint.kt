package br.edu.jonathangs.pokmontcgdeveloper.network

import br.edu.jonathangs.pokmontcgdeveloper.network.data.Error
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class Endpoint(private val service: WebService) {

    fun allSets() = safeCall { service.allSets() }

    fun setCards(setCode: String) = safeCall { service.setCards(setCode) }

    private inline fun <T> safeCall(call: () -> Call<T>): RequestStatus<T> {
        return try {
            val response = call().execute()
            if (response.isSuccessful)
                RequestStatus.Success(response.body())
            else
                RequestStatus.Failure(errorResponse(response))
        } catch (cause: Exception) {
            RequestStatus.Failure(NetworkException("Unexpected", cause))
        }
    }

    private fun <T> errorResponse(response: Response<T>): NetworkException {
        return if (response.errorBody() == null)
            ErrorException("No error body")
        else {
            val json = toJson(response)
            ErrorException(json.error)
        }
    }

    private fun <T> toJson(response: Response<T>) =
        Gson().fromJson(response.errorBody()?.string(), Error::class.java)

}