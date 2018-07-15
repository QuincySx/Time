package com.smallraw.time.entity

public data class Weather(
        val place: String,
        val parent_city: String,
        val admin_area: String,
        val cloud: String,
        val cond_code: String,
        val cond_txt: String,
        val fl: String,
        val tmp: String,
        val pcpn: String
)