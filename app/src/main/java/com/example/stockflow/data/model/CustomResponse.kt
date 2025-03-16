package com.example.stockflow.data.model

data class CustomResponse<T>(

    var status: Boolean,
    var message: String? = null,
    var data: T?

)