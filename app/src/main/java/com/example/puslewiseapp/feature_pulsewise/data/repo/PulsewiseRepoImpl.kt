package com.example.puslewiseapp.feature_pulsewise.data.repo

import androidx.lifecycle.LiveData
import com.example.puslewiseapp.feature_pulsewise.data.di.IoDispatcher
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDao
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem
import kotlinx.coroutines.CoroutineDispatcher

class PulsewiseRepoImpl(
    private val dao: PulsewiseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    val allPulsewiseItems: LiveData<List<LocalPulsewiseItem>> = dao.getAllPulsewiseItems()

    suspend fun addPulsewiseItem(pulsewiseItem: LocalPulsewiseItem){
        dao.addPulsewiseItem(pulsewiseItem)
    }
    suspend fun delete(pulsewiseItem: LocalPulsewiseItem){
        dao.deletePulsewiseItem(pulsewiseItem)
    }

    suspend fun update(pulsewiseItem: LocalPulsewiseItem){
        dao.update(pulsewiseItem.id, pulsewiseItem.systolic, pulsewiseItem.diastolic, pulsewiseItem.pulse)
    }

}