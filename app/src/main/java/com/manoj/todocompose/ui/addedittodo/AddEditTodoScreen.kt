package com.manoj.todocompose.ui.addedittodo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manoj.todocompose.data.AddEditEvent
import com.manoj.todocompose.data.UiEvent
import com.manoj.todocompose.viewmodel.AddEditTodoViewmodel

@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit, viewModel: AddEditTodoViewmodel = hiltViewModel()
) {
    val scaffoldState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onPopBackStack()
                    }
                }

                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditEvent.OnSaveClick)
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Todo")
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {

            TextField(value = viewModel.title, onValueChange = { title ->
                viewModel.onEvent(
                    AddEditEvent.OnTitleChange(
                        title
                    )
                )
            }, placeholder = { Text(text = "Title") }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = viewModel.description, onValueChange = { description ->
                viewModel.onEvent(
                    AddEditEvent.OnDescriptionChange(
                        description
                    )
                )
            }, placeholder = { Text(text = "Description") }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}