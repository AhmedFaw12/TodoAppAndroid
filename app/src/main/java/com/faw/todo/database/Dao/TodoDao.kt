package com.faw.todo.database.Dao

import androidx.room.*
import com.faw.todo.database.model.Todo
import java.util.*

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

    @Query("SELECT * from TodoTable where date = :date")
    fun getTodosByDate(date: Date):List<Todo>
}