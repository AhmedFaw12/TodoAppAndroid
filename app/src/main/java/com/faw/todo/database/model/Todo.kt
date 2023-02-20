package com.faw.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TodoTable")
data class Todo (
    @PrimaryKey(autoGenerate = true) val id:Int ?= null,
    @ColumnInfo
    var todoName:String ?= null,
    var todoDescription:String ?= null,
    var date:Date?= null,
    var isDone: Boolean ?= null
):java.io.Serializable