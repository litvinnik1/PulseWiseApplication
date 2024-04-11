package com.example.puslewiseapp.feature_pulsewise.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "pulsewise")
data class LocalPulsewiseItem(
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val date: String,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int?
) : Serializable
