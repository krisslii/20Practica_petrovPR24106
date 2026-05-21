package com.example.pracitca20_pavlenko

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val age: String? = null,
    val city: String? = null
)