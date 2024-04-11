package com.example.puslewiseapp.feature_pulsewise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem

@Database(
    entities = [LocalPulsewiseItem::class],
    version = 3,
    exportSchema = false
)
abstract class PulsewiseDatabase: RoomDatabase() {
    abstract val dao: PulsewiseDao

    companion object{
        const val DATABASE_NAME = "pulsewise_db"
    }
}