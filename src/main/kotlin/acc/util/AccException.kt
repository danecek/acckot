package acc.util

class AccException : Exception {

    constructor(message: String, ex: Exception) : super(message, ex)
    constructor(cause: Exception) : super(cause)

}
