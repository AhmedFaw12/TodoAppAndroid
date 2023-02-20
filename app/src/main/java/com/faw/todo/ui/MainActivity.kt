package com.faw.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.faw.todo.R
import com.faw.todo.databinding.ActivityMainBinding
import com.faw.todo.ui.fragments.AddTaskBottomSheetFragment
import com.faw.todo.ui.fragments.SettingsFragment
import com.faw.todo.ui.fragments.TodoListFragment
import com.faw.todo.ui.listeners.OnDismissListener

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    val todoListFragment =  TodoListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViews()
        activityMainBinding.addTodo.setOnClickListener{
            showAddTaskBottomFragment()
        }
    }


    fun initViews(){

        activityMainBinding.mainBottomNavigationView.setOnItemSelectedListener {
            if(it.itemId == R.id.navigation_settings){
                pushFragment(SettingsFragment())
            }else if(it.itemId == R.id.navigation_list){
                pushFragment(todoListFragment)
            }
            return@setOnItemSelectedListener true
        }

        activityMainBinding.mainBottomNavigationView.selectedItemId = R.id.navigation_list
    }

    fun showAddTaskBottomFragment(){
        val addTaskBottomSheet = AddTaskBottomSheetFragment()
        addTaskBottomSheet.show(supportFragmentManager, null)
        addTaskBottomSheet.onDismissListener = OnDismissListener {
            //add task bottom sheet dragment dismissed
            //get tasks lists fragment
            //reload tasks
            todoListFragment.loadTasks()
        }
    }

    fun pushFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}