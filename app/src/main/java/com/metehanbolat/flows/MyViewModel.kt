package com.metehanbolat.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

            /*
            // Sonuncuyu göster demek değil bu eğer veri geliyorsa onu bekler eğer zaten gelmişse tek tek görürüz.
            // Örneğin delay yazarsak sadece 0 değerini görüyorken delay yazmazsak 10 dan geriye sayar.
            countDownTimerFlow.collectLatest {
                delay(2000)
                println("Counter is: $it")
            }
             */
        }


        /*
        // Yukarıda yapılan işlem yerine onEach metodu da kullanılabilir.
        countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)
         */
    }

    // LiveData comparisons

    // MutableLiveData'da ilk değer vermek zorunda değiliz
    private val _liveData = MutableLiveData<String>("KotlinLiveData")
    val liveData : LiveData<String> = _liveData

    // State'lerde ilk değer vermek zorundayız.
    private val _stateFlow = MutableStateFlow("KotlinStateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    // Shared Flow'da ilk değer verilmez. Emit vardır ve coroutinescope gerekir. Modifiye edilebilir yapıdadır.
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeLiveDataValue() {
        _liveData.value = "Live Data"
    }

    fun changeStateFlowValue() {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }
}