package com.android.sunshineapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.android.sunshineapp.repository.SunshineRepository

class MainViewModelFactory(private val repository: SunshineRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository)as T
    }
}