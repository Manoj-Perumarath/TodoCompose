package com.manoj.todocompose.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun getTodos(): Flow<List<Todo>> {
          return  dao.getTodos()
    }
}