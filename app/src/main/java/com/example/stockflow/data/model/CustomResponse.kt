package com.example.stockflow.data.model

data class CustomResponse<T>(

    var status: String,
    var message: String? = null,
    var data: T?

)