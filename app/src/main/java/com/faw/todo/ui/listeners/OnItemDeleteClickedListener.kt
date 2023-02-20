package com.faw.todo.ui.listeners

import com.faw.todo.database.model.Todo

fun interface OnItemDeleteClickedListener {
    fun onItemDeleteClicked(task: Todo, position:Int)
}