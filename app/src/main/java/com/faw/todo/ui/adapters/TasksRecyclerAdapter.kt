package com.faw.todo.ui.adapters

import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.faw.todo.R
import com.faw.todo.database.model.Todo
import com.faw.todo.databinding.ItemTaskBinding
import com.faw.todo.ui.listeners.OnItemDeleteClickedListener
import com.faw.todo.ui.listeners.OnTaskItemClickListener
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class TasksRecyclerAdapter(var items:List<Todo>?):Adapter<TasksRecyclerAdapter.ViewHolder>() {

    var onCheckImageClickListener:OnTaskItemClickListener ?= null
    var onTaskClickListener:OnTaskItemClickListener ?= null
    var onItemDeleteClickedListener : OnItemDeleteClickedListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = items?.get(position)?.todoName
        holder.viewBinding.desc.text = items?.get(position)?.todoDescription
        holder.viewBinding.time.text = formatDate(items?.get(position)?.date)
        setCheckableViews(holder, position)



        holder.viewBinding.checkImg.setOnClickListener{
            onCheckImageClickListener?.onItemClick(items?.get(position)!!)
        }

        holder.viewBinding.card.setOnClickListener{
            onTaskClickListener?.onItemClick(items?.get(position)!!)
        }
        holder.viewBinding.delete.setOnClickListener{
            onItemDeleteClickedListener?.onItemDeleteClicked(items?.get(position)!!, position)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }



    fun changeData(newListOfItems:List<Todo>?){
        items = newListOfItems
        notifyDataSetChanged()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setCheckableViews(holder: ViewHolder, position: Int){
        if(items?.get(position)?.isDone == true) {
            holder.viewBinding.checkImg.visibility = View.INVISIBLE
            val doneColor = ContextCompat.getColor(
                holder.viewBinding.root.context,
                R.color.green
            );
            holder.viewBinding.doneText.visibility = View.VISIBLE
            holder.viewBinding.startLine.setBackgroundColor(doneColor)
            holder.viewBinding.title.setTextColor(doneColor)

        } else{
            holder.viewBinding.checkImg.visibility = View.VISIBLE
            holder.viewBinding.doneText.visibility = View.INVISIBLE
            val notDoneColor = ContextCompat.getColor(
                holder.viewBinding.root.context,
                R.color.colorPrimary
            );
            holder.viewBinding.title.setTextColor(notDoneColor)
            holder.viewBinding.startLine.setBackgroundColor(notDoneColor)
        }
    }

    private fun formatDate(date: Date?):String?{
        val dateFormatter = SimpleDateFormat("E/dd/MMM/yyyy ")
        return dateFormatter.format(date) ?: null
    }
    class ViewHolder(val viewBinding:ItemTaskBinding):androidx.recyclerview.widget.RecyclerView.ViewHolder(viewBinding.root){

    }



}

