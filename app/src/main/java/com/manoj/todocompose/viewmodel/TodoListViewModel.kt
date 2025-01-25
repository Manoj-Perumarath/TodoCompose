package com.manoj.todocompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoj.todocompose.data.Todo
import com.manoj.todocompose.data.TodoListEvent
import com.manoj.todocompose.data.TodoRepository
import com.manoj.todocompose.data.UiEvent
import com.manoj.todocompose.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        viewModelScope.launch {
            todoRepository.getTodos().collect { fetchedTodos ->
                _todos.value = fetchedTodos
            }
        }
    }

    private var deletedTodo: Todo? = null
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }

            is TodoListEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    todoRepository.deleteTodo(event.todo)
                }
                sendUiEvent(UiEvent.ShowSnackBar("Todo Deleted", "Undo"))
            }

            TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
//                _uiEvent.send(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        event.todo.copy(isDone = event.isDone)
                    )
                }
            }

            TodoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}