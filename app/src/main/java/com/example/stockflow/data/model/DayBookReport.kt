package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class DayBookReport(

    @SerializedName("id")
    val id: String ?= null,

    @SerializedName("date")
    val date: String,

    @SerializedName("moneyIn")
    val moneyIn: Float,

    @SerializedName("moneyInCash")
    val moneyInCash: Float,

    @SerializedName("moneyInCheque")
    val moneyInCheque: Float,

    @SerializedName("moneyInUPI")
    val moneyInUPI: Float,

    @SerializedName("moneyOut")
    val moneyOut: Float,

    @SerializedName("moneyOutCash")
    val moneyOutCash: Float,

    @SerializedName("moneyOutCheque")
    val moneyOutCheque: Float,

    @SerializedName("moneyOutUPI")
    val moneyOutUPI: Float
)