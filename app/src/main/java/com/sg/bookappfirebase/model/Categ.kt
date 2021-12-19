package com.sg.bookappfirebase.model

import com.google.firebase.Timestamp

data class Categ(
    val categoryName: String,
    val timestamp: Timestamp?,
    val documentID: String,
)