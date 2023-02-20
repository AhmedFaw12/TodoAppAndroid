package com.faw.todo.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.faw.todo.database.MyDataBase
import com.faw.todo.databinding.FragmentListBinding
import com.faw.todo.ui.DayViewContainer
import com.faw.todo.ui.adapters.TasksRecyclerAdapter
import com.kizitonwose.calendar.core.*

import com.kizitonwose.calendar.view.WeekDayBinder
import com.faw.todo.R
import com.faw.todo.base.BaseFragment
import com.faw.todo.database.model.Todo
import com.faw.todo.ui.Constants
import com.faw.todo.ui.EditActivity
import com.faw.todo.ui.clearTime
import com.faw.todo.ui.listeners.OnItemDeleteClickedListener
import com.faw.todo.ui.listeners.OnTaskItemClickListener
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class TodoListFragment:BaseFragment() {

    lateinit var viewBinding: FragmentListBinding
    lateinit var tasksAdapter :TasksRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentListBinding.inflate(inflater, container , false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalenderView()
        tasksAdapter = TasksRecyclerAdapter(null)
        viewBinding.tasksRecyclerView.adapter = tasksAdapter
        checkImageDonetaskListener()
        setTaskListener()
        itemDeleteListener()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }



    fun loadTasks(){
        if(!isResumed){
            return
        }
         val tasks = MyDataBase.getDataBaseInstance(requireActivity())
             .getTodoDao()
             .getTodos()
        tasksAdapter.changeData(tasks)

    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTask(task:Todo, date:WeekDay?= null){
        showMessage(
            "Do you want to delete this task",
            positiveActionTitle = "Yes",
            positiveAction = {dialog,_ ->

                MyDataBase
                    .getDataBaseInstance(requireActivity())
                    .getTodoDao()
                    .deleteTodo(task)
                if(date != null){
                    loadTasksByDate(date!!)
                }else{
                    loadTasks()
                }
                dialog.dismiss()
            },
            negativeActionTitle = "No",
            negativeAction = {dialog,_ ->
                dialog.dismiss()
            }


        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun itemDeleteListener(date:WeekDay ?=null){
        tasksAdapter.onItemDeleteClickedListener = OnItemDeleteClickedListener{
            task, position ->
            showToast(requireContext(),"Item Delete Clicked")
            deleteTask(task, date)

        }
    }

    fun makeTodoDone(task:Todo){
        task.isDone = true
        MyDataBase
            .getDataBaseInstance(requireActivity())
            .getTodoDao()
            .updateTodo(task)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkImageDonetaskListener(data:WeekDay?= null){
        tasksAdapter.onCheckImageClickListener =
            OnTaskItemClickListener { task ->
                showToast(requireContext(), "Check Image Clicked")
                makeTodoDone(task)
                if (data != null){
                    loadTasksByDate(data!!)
                }else{
                    loadTasks()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setTaskListener(data:WeekDay?= null){
        tasksAdapter.onTaskClickListener =
            OnTaskItemClickListener { task ->
                showToast(requireContext(),"Item Clicked")
                showMessage(
                    "What do you want to do ?",
                    "update",
                    {_,i -> updateTodo(task)},
                    "Make done",
                    {_, i ->
                        makeTodoDone(task)
                        if (data != null){
                            loadTasksByDate(data!!)
                        }else{
                            loadTasks()
                        }
                    }
                )
            }
    }

    private fun updateTodo(task: Todo) {
        var intent = Intent(requireActivity(), EditActivity::class.java)
        intent.putExtra(Constants.TASK, task)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initCalenderView(){
        viewBinding.calendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, data: WeekDay) {
                container.dayTextView.text = data.date.dayOfMonth.toString()

                container.dayOfWeekTextView.text = data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

                //container.dayOfWeekTextView.text = data.date.dayOfWeek.toString().substring(0,3)

                //date selection
                dateSelection(container, data)
                setSelectedDateLogic(container, data)

            }
        }

        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth()
        val daysOfWeek = daysOfWeek()

        viewBinding.calendarView.setup(startDate, endDate, daysOfWeek.first())
        viewBinding.calendarView.scrollToWeek(currentDate)
    }

    val calendar = Calendar.getInstance()
    init{
        calendar.clearTime()
    }
    @RequiresApi(Build.VERSION_CODES.O)
     fun loadTasksByDate(data: WeekDay){
        calendar.set(Calendar.YEAR , data.date.year)
        calendar.set(Calendar.MONTH , data.date.monthValue-1)
        calendar.set(Calendar.DAY_OF_MONTH , data.date.dayOfMonth)
        calendar.clearTime()
        Log.e("data:WeekDay", data.toString())
        Log.e("calendar", calendar.toString())
        val tasksByDate = MyDataBase
            .getDataBaseInstance(requireActivity())
            .getTodoDao()
            .getTodosByDate(calendar.time)
        tasksAdapter.changeData(tasksByDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setSelectedDateLogic(container:DayViewContainer, data:WeekDay){
        if(selectedDate == data.date){
            container.dayTextView.setTextAppearance(
                R.style.CalenderViewItemDayAndDayOfWeekSelectedStyle
            )
            container.dayOfWeekTextView.setTextAppearance(
                R.style.CalenderViewItemDayAndDayOfWeekSelectedStyle
            )
            loadTasksByDate(data)
            checkImageDonetaskListener(data)
            setTaskListener(data)
            itemDeleteListener(data)

        }else{
            container.dayTextView.setTextAppearance(
                R.style.CalenderViewItemDayAndDayOfWeekUnSelectedStyle
            )
            container.dayOfWeekTextView.setTextAppearance(
                R.style.CalenderViewItemDayAndDayOfWeekUnSelectedStyle
            )
        }
    }

    //date selection
    var selectedDate: LocalDate? = null
    @RequiresApi(Build.VERSION_CODES.M)
    private fun dateSelection(container: DayViewContainer, data: WeekDay){

        container.view.setOnClickListener {
            // Keep a reference to any previous selection
            // in case we overwrite it and need to reload it.
            val currentSelection = selectedDate
            if (currentSelection == data.date) {
                // If the user clicks the same date, clear selection.
                selectedDate = null

                // Reload this date so the dayBinder is called
                // and we can REMOVE the selection background.
                loadTasks()//when date is unselected get all tasks
                viewBinding.calendarView.notifyDateChanged(currentSelection)

            } else {
                selectedDate = data.date
                // Reload the newly selected date so the dayBinder is
                // called and we can ADD the selection background.
                viewBinding.calendarView.notifyDateChanged(data.date)
                Log.e("Selected date", "Date is Selected")

                if (currentSelection != null) {
                    // We need to also reload the previously selected
                    // date so we can REMOVE the selection background.
                    viewBinding.calendarView.notifyDateChanged(currentSelection)
                }

            }
        }
    }
}