package com.faw.todo.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faw.todo.R
import com.faw.todo.database.MyDataBase
import com.faw.todo.database.model.Todo
import com.faw.todo.databinding.ActivityEditBinding
import com.faw.todo.ui.Constants.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivityEditBinding
    private lateinit var task :Todo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        task = (intent.getSerializableExtra(TASK) as? Todo)!!
        showExistingData(task)
        viewBinding.submitEditButton.setOnClickListener{
            updateTodo()
        }

        viewBinding.date.setOnClickListener{
            showDatePicker()
        }
    }

    private fun isDataValid():Boolean{
        var isValid = true
        var title = viewBinding.titleContainer.editText?.text.toString()
        var desc = viewBinding.descContainer.editText?.text.toString()
        var date = viewBinding.descContainer.editText?.text.toString()

        if(title.isNullOrBlank()){
            viewBinding.titleContainer.error = "please Enter title"
            isValid = false
        }else{
            viewBinding.titleContainer.error = null
        }

        if(desc.isNullOrBlank()){
            viewBinding.descContainer.error = "please Enter Description"
            isValid = false
        }else{
            viewBinding.descContainer.error = null
        }

        if(date.isNullOrBlank()){
            viewBinding.dateContainer.error = "please Enter Date"
            isValid = false
        }else{
            viewBinding.dateContainer.error = null
        }
        return isValid
    }

    private fun updateTodo() {
        if(isDataValid()){
            task.todoName = viewBinding.titleContainer.editText?.text.toString()
            task.todoDescription = viewBinding.descContainer.editText?.text.toString()
            task.date = parseDate(viewBinding.date.text.toString())

            MyDataBase
                .getDataBaseInstance(this)
                .getTodoDao()
                .updateTodo(task)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDate(){
        var formatter = SimpleDateFormat("dd - MM - yyyy")
        viewBinding.date.text = formatter.format(currentDate.time)
    }

    var currentDate: Calendar = Calendar.getInstance()
    init{
        currentDate.clearTime()
    }
    private fun showDatePicker() {
        val myDatePicker = DatePickerDialog(this,
            { _, year, month, day ->
                currentDate.set(Calendar.YEAR, year)
                currentDate.set(Calendar.MONTH, month)
                currentDate.set(Calendar.DAY_OF_MONTH, day)
                setDate()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        myDatePicker.show()

    }

    private fun showExistingData(task: Todo) {
        viewBinding.titleContainer.editText?.setText(task.todoName)
        viewBinding.descContainer.editText?.setText(task.todoDescription)
        viewBinding.date.text = formatDate(task.date)
    }

    private fun formatDate(date: Date?) :String{
        val format = SimpleDateFormat("dd - MM - yyyy")
        return format.format(date)
    }

    private fun parseDate(date: String) :Date?{
        val format = SimpleDateFormat("dd - MM - yyyy")
        return format.parse(date)
    }


}