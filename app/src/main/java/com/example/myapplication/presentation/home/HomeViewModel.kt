package com.example.myapplication.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.UseCaseResult
import com.example.myapplication.data.entities.User
import com.example.myapplication.domain.usecase.home.LoadUsersUseCase
import com.example.myapplication.tools.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val loadUsersUseCase: LoadUsersUseCase
) : ViewModel() {


    private var currentNumber = 0

    val showCurrentNumber = MutableLiveData<Int>()

    val shouldShowUser = MutableLiveData<User>()
    val shouldShowError = SingleLiveEvent<String>()

    fun loadUser() {
        viewModelScope.launch {
            val result = loadUsersUseCase.execute()
            when (result) {
                is UseCaseResult.Success -> {
                    shouldShowUser.value = result.data
                }
                is UseCaseResult.Error -> {
                    shouldShowError.value = "error"
                }
            }
        }
    }

    fun onBtnIncreaseClick() {
        currentNumber++
        showCurrentNumber.value = currentNumber
    }
}