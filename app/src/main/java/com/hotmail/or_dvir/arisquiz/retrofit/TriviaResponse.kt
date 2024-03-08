package com.hotmail.or_dvir.arisquiz.retrofit

sealed class TriviaResponse<T>(val responseCode: Int, val data: T? = null) {
    class Success<T>(data: T) : TriviaResponse<T>(CODE_SUCCESS, data)

    /**
     * Could not return results. The API doesn't have enough questions for your query.
     * (Ex. Asking for 50 Questions in a Category that only has 20.)
     */
    class NoResult<T> : TriviaResponse<T>(CODE_NO_RESULT)

    /**
     * Contains an invalid parameter. Arguments passed in aren't valid. (Ex. Amount = Five)
     */
    class InvalidParameter<T> : TriviaResponse<T>(CODE_INVALID_PARAMETER)

    /**
     * Session Token does not exist.
     */
    class TokenNotFound<T> : TriviaResponse<T>(CODE_TOKEN_NOT_FOUND)

    /**
     * Session Token has returned all possible questions for the specified query.
     * Resetting the Token is necessary.
     */
    class TokenEmpty<T> : TriviaResponse<T>(CODE_TOKEN_EMPTY)

    /**
     * Too many requests have occurred. Each IP can only access the API once every 5 seconds
     */
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
