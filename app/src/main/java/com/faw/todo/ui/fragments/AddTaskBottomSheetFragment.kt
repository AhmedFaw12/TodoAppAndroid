package com.faw.todo.ui.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import com.faw.todo.R
import com.faw.todo.base.BaseBottomSheet
import com.faw.todo.database.MyDataBase
import com.faw.todo.database.model.Todo
import com.faw.todo.databinding.FragmentAddTaskBinding
import com.faw.todo.ui.clearTime
import com.faw.todo.ui.listeners.OnDismissListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTaskBottomSheetFragment:BaseBottomSheet() {
    lateinit var viewBinding:FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container,false)
        return viewBinding.root
    }

    var onDismissListener:OnDismissListener ?= null
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //dismiss for add task sheet
        onDismissListener?.onDismiss()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        viewBinding.taskDate.setOnClickListener{
            showDatePicker()
        }
        viewBinding.addTaskSubmit.setOnClickListener{
            addTask()
        }
    }

    private fun validateForm():Boolean{
        var valid = true;

        val title = viewBinding.taskTitle.editText?.text.toString()
        val desc = viewBinding.taskDesc.editText?.text.toString()
        //if(title == null || title.trim().isEmpty())
        if(title.isNullOrBlank()){
            //set error
            viewBinding.taskTitle.error = "Please Enter Title"
            valid = false
        }else{
            //remove error
            viewBinding.taskTitle.error = null
        }

        if(desc.isNullOrBlank()){
            //set error
            viewBinding.taskDesc.error = "Please Enter Description"
            valid = false
        }else{
            //remove error
            viewBinding.taskDesc.error = null
        }
        return valid
    }

    private fun addTask(){

        if(!validateForm()){
            return
        }
        showLoadingDialog()
        val title = viewBinding.taskTitle.editText?.text.toString()
        val desc = viewBinding.taskDesc.editText?.text.toString()

        //add to database
        MyDataBase.getDataBaseInstance(requireActivity())
            .getTodoDao()
            .insertTodo(
                Todo(
                    todoName = title,
                    todoDescription = desc,
                    date =  currentDate.time //return object of date type
                )
            )
        showTaskInsertedDialog()
        hideLoadingDialog()

    }

    private fun showTaskInsertedDialog(){
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            .setTitle("Success")
            .setMessage(getString(R.string.task_inserted_message))
            .setPositiveButton(
                R.string.ok
            ) { dialog, btnId ->
                dialog.dismiss()//hides alert dialog
                dismiss() //hides our bottomsheetdialog
            }.setCancelable(false)

        alertDialogBuilder.show()

    }

    private fun setDate(){
        var formatter = SimpleDateFormat("d-M-yyyy")
        viewBinding.taskDateText.text = formatter.format(currentDate.time)
    }

    var currentDate: Calendar = Calendar.getInstance()
    init{
        currentDate.clearTime()
    }
    private fun showDatePicker() {
        val myDatePicker = DatePickerDialog(requireActivity(),
        { datePicker, year, month, day ->
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
}