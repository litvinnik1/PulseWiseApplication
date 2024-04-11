package com.example.puslewiseapp.feature_pulsewise.data.di

import android.content.Context
import androidx.room.Room
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDao
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDatabase
import com.example.puslewiseapp.feature_pulsewise.data.repo.PulsewiseRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PulsewiseModule {

    @Provides
    fun providesRoomDao(database: PulsewiseDatabase): PulsewiseDao{
        return database.dao
    }


    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): PulsewiseDatabase{
        return Room.databaseBuilder(
            appContext.applicationContext,
            PulsewiseDatabase::class.java,
            "pulsewise_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesPomodoroRepo(db: PulsewiseDatabase, @IoDispatcher dispatcher: CoroutineDispatcher): PulsewiseRepoImpl {
        return PulsewiseRepoImpl(db.dao, dispatcher)
    }
}