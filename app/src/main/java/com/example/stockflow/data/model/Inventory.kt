package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Inventory(

    @SerializedName("id")
    val id: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("sell_price")
    val sellPrice: Float,

    @SerializedName("sell_price_unit")
    val sellPriceUnit: String,

    @SerializedName("category")
    val itemCategory: String,

    @SerializedName("mrp")
    val mrp: Float,

    @SerializedName("purchase_price")
    val purchasePrice: Float,

    @SerializedName("tax")
    val tax: Double,

    @SerializedName("item_code")
    val itemCode: String,

    @SerializedName("barcode")
    val barcode: String,

    @SerializedName("item_description")
    val itemDescription: String,

    @SerializedName("low_stock_alert_quantity")
    val lowStockAlert: Int,

    @SerializedName("storage_location")
    val storageLocation: String,

    @SerializedName("expiry_date")
    val expireDate: String

)