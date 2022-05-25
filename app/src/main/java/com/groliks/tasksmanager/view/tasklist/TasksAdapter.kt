package com.groliks.tasksmanager.view.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.groliks.tasksmanager.R
import com.groliks.tasksmanager.data.tasks.model.TaskResponse
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.databinding.ItemTaskBinding
import com.groliks.tasksmanager.databinding.ItemTasksListBinding

class TasksAdapter(
    private val onDelete: (Int) -> Unit,
    private val onComplete: (Int) -> Unit,
    private val onUpdate: (String, Int) -> Unit,
): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var tasks: MutableList<TaskResponse> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    fun submitList(newTasks: List<TaskResponse>) {
        tasks = newTasks.toMutableList()
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        private var task: TaskResponse? = null

        init {
            binding.deleteTask.setOnClickListener {
                task?.also { task ->
                    onDelete(task.id)
                }
            }
            binding.editTask.setOnClickListener {
                task?.also { task ->
                    onUpdate(task.name, task.id)
                }
            }
            binding.completeTask.setOnClickListener {
                task?.also { task ->
                    onComplete(task.id)
                }
            }
        }

        fun bind(task: TaskResponse) {
            binding.taskName.text = task.name
            if (task.isCompleted) {
                binding.completeTask.setImageResource(R.drawable.ic_checked)
                binding.completeTask.isClickable = false
            } else {
                binding.completeTask.setImageResource(R.drawable.ic_not_checked)
                binding.completeTask.isClickable = true
            }
            this.task = task
        }
    }
}