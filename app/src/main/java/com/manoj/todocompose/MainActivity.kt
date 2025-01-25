package com.manoj.todocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.manoj.todocompose.ui.addedittodo.AddEditTodoScreen
import com.manoj.todocompose.ui.list.TodoListScreen
import com.manoj.todocompose.ui.theme.TodoComposeTheme
import com.manoj.todocompose.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                    Log.d("MainActivity", "Nav changed")
                    composable(Routes.TODO_LIST) {
                        TodoListScreen(onNavigate = { navController.navigate(it.route) })
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(navArgument(name = "todoId") {
                            type = NavType.IntType
                            defaultValue = -1
                        })
                    ) {
                        AddEditTodoScreen(onPopBackStack = { navController.popBackStack() })

                    }
                }
            }
        }
    }
}