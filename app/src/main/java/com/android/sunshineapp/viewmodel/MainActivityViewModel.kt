package com.android.sunshineapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.android.sunshineapp.data.database.WeatherEntry
import com.android.sunshineapp.model.WeatherList
import com.android.sunshineapp.repository.SunshineRepository

class MainActivityViewModel(private val repository: SunshineRepository):ViewModel() {
    /**
     * from db
     */
    fun getAllWords():LiveData<List<WeatherEntry>>{
        return repository.getAllWords()
    }
    /**
     * insert word on db
     */
    fun insertWord(word:WeatherEntry){
        repository.addWord(word)
    }
    /**
     * from network
     */
    /*fun getCurrentData():LiveData<List<WeatherList>>{
        return repository.getCurrentData()
    }*/

}