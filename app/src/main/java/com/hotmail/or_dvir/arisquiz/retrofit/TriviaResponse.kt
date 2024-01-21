package com.hotmail.or_dvir.arisquiz.retrofit

sealed class TriviaResponse<T>(val responseCode: Int, val data: T? = null) {
    class Success<T>(data: T) : TriviaResponse<T>(CODE_SUCCESS, data)
    class NoResult<T> : TriviaResponse<T>(CODE_NO_RESULT)
    class InvalidParameter<T> : TriviaResponse<T>(CODE_INVALID_PARAMETER)
    class TokenNotFound<T> : TriviaResponse<T>(CODE_TOKEN_NOT_FOUND)
    class TokenEmpty<T> : TriviaResponse<T>(CODE_TOKEN_EMPTY)
    class RateLimit<T> : TriviaResponse<T>(CODE_RATE_LIMIT)

    class Exception<T> : TriviaResponse<T>(CODE_EXCEPTION)
    class HttpError<T>(responseCode: Int, val message: String?) : TriviaResponse<T>(responseCode)

    companion object {
        private const val CODE_SUCCESS = 0
        private const val CODE_NO_RESULT = 1
        private const val CODE_INVALID_PARAMETER = 2
        private const val CODE_TOKEN_NOT_FOUND = 3
        private const val CODE_TOKEN_EMPTY = 4
        private const val CODE_RATE_LIMIT = 5
        private const val CODE_EXCEPTION = -1

        // todo investigate more about these response codes... the api claims to attach these, but where?!
        fun <T> fromResponseCode(code: Int, data: T, message: String?): TriviaResponse<T> =
            when (code) {
                0, 200 -> Success(data)
                1 -> NoResult()
                2 -> InvalidParameter()
                3 -> TokenNotFound()
                4 -> TokenEmpty()
                5 -> RateLimit()
                else -> HttpError(code, message)
            }
    }
}
