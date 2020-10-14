package br.edu.jonathangs.pokmontcgdeveloper.data.remote

open class NetworkException: Exception {

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

}