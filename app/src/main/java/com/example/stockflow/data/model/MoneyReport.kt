package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class MoneyReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("partyName")
    val name: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("isMoneyInReport")
    val moneyType: Boolean,

    @SerializedName("mode")
    val mode: String
)