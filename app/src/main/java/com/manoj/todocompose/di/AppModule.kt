package com.manoj.todocompose.di

import android.app.Application
import androidx.room.Room
import com.manoj.todocompose.data.TodoDatabase
import com.manoj.todocompose.data.TodoRepository
import com.manoj.todocompose.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.dao)
    }


}