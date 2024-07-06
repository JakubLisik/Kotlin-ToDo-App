package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value?.add(task)
        _tasks.value = _tasks.value // Trigger LiveData update
    }

    fun deleteTask(task: Task) {
        _tasks.value?.remove(task)
        _tasks.value = _tasks.value // Trigger LiveData update
    }

    fun updateTaskStatus(task: Task, isCompleted: Boolean) {
        val index = _tasks.value?.indexOf(task)
        if (index != null && index >= 0) {
            _tasks.value?.get(index)?.isCompleted = isCompleted
            _tasks.value = _tasks.value // Trigger LiveData update
        }
    }

    fun updateTask(oldTask: Task, newTask: Task) {
        val index = _tasks.value?.indexOf(oldTask)
        if (index != null && index >= 0) {
            _tasks.value?.set(index, newTask)
            _tasks.value = _tasks.value // Trigger LiveData update
        }
    }
}