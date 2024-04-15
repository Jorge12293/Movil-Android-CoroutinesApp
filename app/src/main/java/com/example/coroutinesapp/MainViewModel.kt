package com.example.coroutinesapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(){
    var resultState by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set

    init {
        fetchData()
    }

    // Recommended
    fun fetchData(){
        viewModelScope.launch {
            try {
                isLoading = true
                callApi()
            }catch (e:Exception){
                println("Error ${e.message}")
            }finally {
                isLoading = false
            }
        }
    }

    private suspend fun callApi(){
        val result = withContext(Dispatchers.IO){
            delay(5000)
            "Response of the API"
        }
        resultState = result;
    }



    // Crash App
    fun blockApp() {
        Thread.sleep(5000)
        resultState = "Response of the API"
    }
}