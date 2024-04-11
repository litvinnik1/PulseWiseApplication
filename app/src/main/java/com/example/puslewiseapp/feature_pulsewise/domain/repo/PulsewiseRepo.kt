package com.example.puslewiseapp.feature_pulsewise.domain.repo

import com.example.puslewiseapp.feature_pulsewise.domain.model.PulsewiseItem


interface PulsewiseRepo {
    suspend fun getAllPulsewiseItems(): List<PulsewiseItem>
    suspend fun getSinglePulsewiseItemById(id: Int): PulsewiseItem?
    suspend fun addPulsewiseItem(pulsewise: PulsewiseItem)
    suspend fun updatePulsewiseItem(pulsewise: PulsewiseItem)
    suspend fun deletePulsewiseItem(pulsewise: PulsewiseItem)
}