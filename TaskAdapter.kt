package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    public var tasks: List<Task>,
    private val onTaskCheckedChange: (Task, Boolean) -> Unit,
    private val onTaskDelete: (Task) -> Unit,
    private val onTaskEdit: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskCheckBox.isChecked = task.isCompleted

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTaskCheckedChange(task, isChecked)
        }

        holder.deleteButton.setOnClickListener {
            onTaskDelete(task)
        }

        holder.itemView.setOnClickListener {
            onTaskEdit(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }
}