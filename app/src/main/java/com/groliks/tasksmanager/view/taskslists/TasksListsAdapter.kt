package com.groliks.tasksmanager.view.taskslists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.databinding.ItemTasksListBinding

class TasksListsAdapter(
    private val onDelete: (Int) -> Unit,
    private val onSelect: (Int) -> Unit,
    private val onUpdate: (String, Int) -> Unit,
) : RecyclerView.Adapter<TasksListsAdapter.TasksListViewHolder>() {
    private var lists: List<TasksListResponse> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTasksListBinding.inflate(inflater, parent, false)
        return TasksListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount() = lists.size

    fun submitList(newLists: List<TasksListResponse>) {
        lists = newLists
        notifyDataSetChanged()
    }

    inner class TasksListViewHolder(
        private val binding: ItemTasksListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var list: TasksListResponse? = null

        init {
            binding.deleteList.setOnClickListener {
                list?.also { list ->
                    onDelete(list.id)
                }
            }
            binding.root.setOnClickListener {
                list?.also { list ->
                    onSelect(list.id)
                }
            }
            binding.editList.setOnClickListener {
                list?.also { list ->
                    onUpdate(list.name, list.id)
                }
            }
        }

        fun bind(list: TasksListResponse) {
            binding.listName.text = list.name
            binding.completionProgress.text = "${list.completionProgress}%"
            this.list = list
        }
    }
}