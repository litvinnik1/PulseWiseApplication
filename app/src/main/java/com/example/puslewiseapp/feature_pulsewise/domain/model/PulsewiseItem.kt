package com.example.puslewiseapp.feature_pulsewise.domain.model

import java.io.Serializable


data class PulsewiseItem (
val systolic: Int,
val diastolic: Int,
val pulse: Int,
val date: String,
val time: String,
val id: Int?
): Serializable