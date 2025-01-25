package com.manoj.todocompose.data

sealed class UiEvent {

    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String?) : UiEvent()
}