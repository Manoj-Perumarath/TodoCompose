package com.manoj.todocompose.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * from todo where id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * from todo")
    suspend fun getTodos(): Flow<List<Todo>>
}