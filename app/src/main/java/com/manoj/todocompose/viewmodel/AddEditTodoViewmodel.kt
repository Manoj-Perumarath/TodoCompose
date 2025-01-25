package com.manoj.todocompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoj.todocompose.data.AddEditEvent
import com.manoj.todocompose.data.Todo
import com.manoj.todocompose.data.TodoRepository
import com.manoj.todocompose.data.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewmodel @Inject constructor(
    private val repository: TodoRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let {
                    title = it.title ?: ""
                    description = it.description ?: ""
                    todo = it
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.OnDescriptionChange -> {
                description = event.newDescription
            }

            AddEditEvent.OnSaveClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "The title can't be empty", action = "Ok"
                            )
                        )
                    } else {
                        repository.insertTodo(
                            Todo(
                                title = title,
                                description = description,
                                isDone = todo?.isDone ?: false,
                            )
                        )
//                        sendUiEvent(UiEvent.PopBackStack)
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "Todo Saved", action = "Ok"
                            )
                        )
                    }
                }
            }

            is AddEditEvent.OnTitleChange -> {
                title = event.newTitle
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}