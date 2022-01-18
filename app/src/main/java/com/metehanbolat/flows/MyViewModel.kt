package com.metehanbolat.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

        // collect verileri toplama işlemi yaptığımız yer yani önce 3 ile bölünenleri filtreleyip ardından collect ile topluyoruz
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it %3 == 0
                }
                // Filtreleme işlemi yaptıktan sonra mapping yaparak farklı değerler elde edebiliriz.
                .map {
                    it + it
                }
                .collect {
                println("Counter is: $it")
            }
        }
    }
}