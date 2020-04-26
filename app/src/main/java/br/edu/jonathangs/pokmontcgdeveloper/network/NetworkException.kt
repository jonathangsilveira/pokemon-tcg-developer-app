package br.edu.jonathangs.pokmontcgdeveloper.network

open class NetworkException: Exception {

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

}