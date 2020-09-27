package br.edu.jonathangs.pokmontcgdeveloper.data.remote

import br.edu.jonathangs.pokmontcgdeveloper.data.Result
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> safeCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful)
            Result.Success(response.body())
        else
            Result.Failure(response.errorResponse())
    } catch (cause: Exception) {
        Result.Failure(NetworkException("Unexpected", cause))
    }
}

suspend fun <T> unsafeCall(call: suspend () -> Response<T>): T? {
    val response = call()
    if (!response.isSuccessful)
        throw response.errorResponse()
    return response.body()
}

private fun <T> Response<T>.errorResponse(): NetworkException {
    return if (this.errorBody() == null)
        ResponseException("${code()} - ${message()}")
    else {
        val json = errorBodyAsJson(this)
        ResponseException(json.error)
    }
}

private fun <T> errorBodyAsJson(response: Response<T>) =
    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
