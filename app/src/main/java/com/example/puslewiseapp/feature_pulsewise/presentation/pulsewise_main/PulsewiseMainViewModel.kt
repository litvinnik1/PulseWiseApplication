package com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puslewiseapp.feature_pulsewise.data.di.IoDispatcher
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDao
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDatabase
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem
import com.example.puslewiseapp.feature_pulsewise.data.repo.PulsewiseRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PulsewiseMainViewModel @Inject constructor(
    private val pulsewiseRepoImpl: PulsewiseRepoImpl,
    private val database: PulsewiseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private var undoPulsewiseItem: LocalPulsewiseItem? = null
    private var getPulsewiseItemsJob: Job? = null
    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    }
    val allPulsewiseItems: LiveData<List<LocalPulsewiseItem>> = pulsewiseRepoImpl.allPulsewiseItems

    fun deletePulsewiseItem(pulsewiseItem: LocalPulsewiseItem){
        viewModelScope.launch (Dispatchers.IO) {
            pulsewiseRepoImpl.delete(pulsewiseItem)
        }
    }

    fun addAllPulsewiseItems(pulsewiseItem: LocalPulsewiseItem){
        viewModelScope.launch(Dispatchers.IO) {
            pulsewiseRepoImpl.addPulsewiseItem(pulsewiseItem)
        }
    }
    fun addPulsewiseItem(pulsewiseItem: LocalPulsewiseItem){
        viewModelScope.launch(Dispatchers.IO) {
            pulsewiseRepoImpl.addPulsewiseItem(pulsewiseItem)
        }
    }
    fun updatePulsewiseItem(pulsewiseItem: LocalPulsewiseItem) {
        viewModelScope.launch(Dispatchers.IO) {
            pulsewiseRepoImpl.update(pulsewiseItem)
        }
    }
}