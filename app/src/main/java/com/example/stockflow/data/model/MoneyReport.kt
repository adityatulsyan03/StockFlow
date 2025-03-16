package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class MoneyReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("party_name")
    val name: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("is_money_in_report")
    val moneyType: Boolean,

    @SerializedName("mode")
    val mode: String
)