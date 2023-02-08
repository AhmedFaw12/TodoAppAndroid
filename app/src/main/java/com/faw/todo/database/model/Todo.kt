package com.faw.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TodoTable")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo
    val todoName:String ?= null,
    val todoDescription:String ?= null,
    val date:Date?= null,
    val isDone: Boolean ?= null
)