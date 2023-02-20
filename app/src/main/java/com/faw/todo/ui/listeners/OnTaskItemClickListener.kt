package com.faw.todo.ui.listeners

import com.faw.todo.database.model.Todo

fun interface OnTaskItemClickListener {
    fun onItemClick(item: Todo)
}