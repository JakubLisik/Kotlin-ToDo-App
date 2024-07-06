package com.example.myapplication

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskAdapter = TaskAdapter(listOf(), { task, isCompleted ->
            taskViewModel.updateTaskStatus(task, isCompleted)
        }, { task ->
            taskViewModel.deleteTask(task)
        }, { task ->
            showEditTaskDialog(task)
        })

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = taskAdapter

        binding.buttonAdd.setOnClickListener {
            val taskName = binding.editTextTask.text.toString()
            if (taskName.isNotBlank()) {
                taskViewModel.addTask(Task(taskName))
                binding.editTextTask.text.clear()
            }
        }

        taskViewModel.tasks.observe(this, Observer { tasks ->
            taskAdapter.tasks = tasks
            taskAdapter.notifyDataSetChanged()
        })
    }

    private fun showEditTaskDialog(task: Task) {
        val editText = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(task.name)
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                val newTaskName = editText.text.toString()
                if (newTaskName.isNotBlank()) {
                    taskViewModel.updateTask(task, Task(newTaskName, task.isCompleted))
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}
