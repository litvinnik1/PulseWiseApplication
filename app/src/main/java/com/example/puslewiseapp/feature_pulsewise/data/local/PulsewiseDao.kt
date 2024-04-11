package com.example.puslewiseapp.feature_pulsewise.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem


@Dao
interface PulsewiseDao {

    @Query("SELECT * FROM pulsewise")
    fun getAllPulsewiseItems(): LiveData<List<LocalPulsewiseItem>>

    @Query("SELECT *  FROM pulsewise WHERE id = :id")
    suspend fun getSinglePulsewiseItemById(id:Int): LocalPulsewiseItem?

    @Query("UPDATE pulsewise SET systolic = :systolic, diastolic = :diastolic, pulse = :pulse WHERE id = :id  ")
    suspend fun update(id: Int?, systolic: Int?, diastolic:Int?, pulse: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPulsewiseItems(pulsewiseItems: List<LocalPulsewiseItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPulsewiseItem(pulsewiseItem: LocalPulsewiseItem): Long

    @Delete
    suspend fun deletePulsewiseItem(pulsewiseItem: LocalPulsewiseItem)
}