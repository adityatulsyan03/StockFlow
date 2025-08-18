package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Inventory(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("photo")
    val photo: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("sellPrice")
    val sellPrice: Float,

    @SerializedName("sellPriceUnit")
    val sellPriceUnit: String,

    @SerializedName("category")
    val itemCategory: String,

    @SerializedName("mrp")
    val mrp: Float,

    @SerializedName("purchasePrice")
    val purchasePrice: Float,

    @SerializedName("tax")
    val tax: Float? = null,

    @SerializedName("itemCode")
    val itemCode: String? = null,

    @SerializedName("barcode")
    val barcode: String? = null,

    @SerializedName("itemDescription")
    val itemDescription: String? = null,

    @SerializedName("lowStockAlertQuantity")
    val lowStockAlert: Int? = null,

    @SerializedName("storageLocation")
    val storageLocation: String? = null,

    @SerializedName("expiryDate")
    val expireDate: String? = null

)