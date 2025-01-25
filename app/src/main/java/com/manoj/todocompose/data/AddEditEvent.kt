package com.manoj.todocompose.data

sealed class AddEditEvent {

    data class OnTitleChange(val newTitle: String) : AddEditEvent()
    data class OnDescriptionChange(val newDescription: String) : AddEditEvent()
    object OnSaveClick : AddEditEvent()
}