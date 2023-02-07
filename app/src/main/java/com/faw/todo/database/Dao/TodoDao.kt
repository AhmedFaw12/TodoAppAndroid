package com.faw.todo.database.Dao

import androidx.room.*
import com.faw.todo.database.model.Todo

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Query("SELECT * FROM TodoTable")
    fun getTodos():List<Todo>
}