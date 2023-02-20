package com.faw.todo.ui

import java.util.Calendar

fun Calendar.clearTime(){
    this.set(Calendar.HOUR, 0)
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)

}