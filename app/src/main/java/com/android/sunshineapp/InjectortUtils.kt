package com.android.sunshineapp

import android.content.Context
import com.android.sunshineapp.data.database.WeatherDatabase
import com.android.sunshineapp.data.network.WeatherNetworkDataSource
import com.android.sunshineapp.repository.SunshineRepository
import com.android.sunshineapp.viewmodel.MainActivityViewModel
import com.android.sunshineapp.viewmodel.MainViewModelFactory

class InjectortUtils {
    companion object {
        private fun provideRepository(context: Context): SunshineRepository {
            val database = WeatherDatabase.getInstanceDB(context.applicationContext)
            val executor = AppExecutor.getInstanceExecutor()
            val networkDataSource = WeatherNetworkDataSource.getInstanceSource(executor)
            return SunshineRepository.getInstanceRepository(database.weatherDao(), networkDataSource, executor)
        }

        fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
            val repository = provideRepository(context.applicationContext)
            return MainViewModelFactory(repository)

        }
    }
}