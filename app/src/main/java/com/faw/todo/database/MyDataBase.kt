package com.faw.todo.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.faw.todo.database.Dao.TodoDao
import com.faw.todo.database.model.Converters
import com.faw.todo.database.model.Todo

@Database(entities = [Todo::class],
    version=1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyDataBase: RoomDatabase() {

    abstract fun getTodoDao() : TodoDao

    companion object{
        private var myDatabaseInstance:MyDataBase ?= null
        private const val dataBaseName = "TodoDB"
        fun getDataBaseInstance(context: Context):MyDataBase{
            if(myDatabaseInstance == null){
                //initialize
                myDatabaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    dataBaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return myDatabaseInstance!!
        }
    }

}