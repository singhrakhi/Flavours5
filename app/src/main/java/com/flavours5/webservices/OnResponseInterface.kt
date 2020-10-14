package com.flavours5.webservices

interface OnResponseInterface {

    fun onApiResponse(response: Any?)
    fun onApiFailure(message: String?)

    fun onApiResponse(response: Any?, requestId: Int = 0) {
        onApiResponse(response)
    }
    fun onApiFailure(message: String?, requestId: Int = 0) {
        onApiFailure(message)
    }
}