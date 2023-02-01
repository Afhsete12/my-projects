package com.example.vlaka.data.models

import java.io.Serializable


class Product: Serializable {

    lateinit var id: String
    lateinit var img: String
    lateinit var name: String
    var type: String? = null ?: "Others"
    var stock: Int = 0
    lateinit var time: String
    var hour: String? = null ?: ""
    var calories: Int = 0

}


class InfoAboutCalories {
    var pStock: Int = 0
    var pCalories: Int = 0
}

