package com.metehanbolat.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    // Flow içerisinde yayılacak ve toplanacak verilerin tipini yazıyoruz.
    // Yapılacak işleri kod bloğunda yazıyoruz.
    // Emit demek veriyi yaymak demek.
    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
    }

    private fun collectInViewModel() {

        viewModelScope.launch {
            countDownTimerFlow.collect {
                println("Counter is: $it")
            }
        }
    }
}