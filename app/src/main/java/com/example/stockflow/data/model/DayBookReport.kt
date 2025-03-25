package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class DayBookReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: LocalDate,

    @SerializedName("money_in")
    val moneyIn: Int,

    @SerializedName("money_in_cash")
    val moneyInCash: Int,

    @SerializedName("money_in_cheque")
    val moneyInCheque: Int,

    @SerializedName("money_in_upi")
    val moneyInUPI: Int,

    @SerializedName("money_out")
    val moneyOut: Int,

    @SerializedName("money_out_cash")
    val moneyOutCash: Int,

    @SerializedName("money_out_cheque")
    val moneyOutCheque: Int,

    @SerializedName("money_out_upi")
    val moneyOutUPI: Int
)