package com.example.martinifoodlabs_grupo3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinifoodlabs_grupo3.navigation.NavigationEvent
import com.example.martinifoodlabs_grupo3.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _eventos = MutableSharedFlow<NavigationEvent>()

    val eventos: SharedFlow<NavigationEvent> = _eventos.asSharedFlow()

    fun navigateTo(
        screen: Screen,
        popUpTo: Screen? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {
        viewModelScope.launch {
            _eventos.emit(NavigationEvent.NavigateTo(screen, popUpTo, inclusive, singleTop))
        }
    }

    fun navigateBack() {
        viewModelScope.launch { _eventos.emit(NavigationEvent.PopBackStack) }
    }

    fun navigateUp() {
        viewModelScope.launch { _eventos.emit(NavigationEvent.NavigateUp) }
    }
}