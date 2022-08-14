package com.realityexpander.viewlifecycleownerdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private var _country = MutableLiveData<String>("")
    val country: LiveData<String> = _country

    fun saveCountry(country: String) {
        _country.value = country
    }
}

