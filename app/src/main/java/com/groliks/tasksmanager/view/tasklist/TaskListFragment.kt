package com.groliks.tasksmanager.view.tasklist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.groliks.tasksmanager.appComponent
import com.groliks.tasksmanager.authToken
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.databinding.FragmentListBinding
import com.groliks.tasksmanager.view.taskslists.TasksListsAdapter
import com.groliks.tasksmanager.view.taskslists.TasksListsFragmentDirections
import com.groliks.tasksmanager.view.taskslists.TasksListsViewModel
import com.groliks.tasksmanager.view.util.EnterNameDialog
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class TaskListFragment: Fragment() {
    private val navArgs: TaskListFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: TaskListViewModel.Factory
    private val viewModel: TaskListViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel.loadTasks(requireContext().authToken!!, navArgs.listId)

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TasksAdapter(
            this::onDelete,
            this::onComplete,
            this::onUpdate
        )
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasks.collect { tasks ->
                adapter.submitList(tasks)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.errors.collect { requestResult ->
                if (requestResult is RequestResult.Error) {
                    Toast.makeText(requireContext(), requestResult.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.addButton.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToEnterNameDialog("")
            findNavController().navigate(action)
        }

        setFragmentResultListener(EnterNameDialog.NEW_NAME_KEY) { _, result ->
            val id = result.getInt(EnterNameDialog.ID_KEY)
            val name = result.getString(EnterNameDialog.NAME_KEY)
            if (id == EnterNameDialog.NEW_NAME_ID) {
                viewModel.createTask(requireContext().authToken!!, name!!, navArgs.listId)
            } else {
                viewModel.updateTask(requireContext().authToken!!, name!!, id, navArgs.listId)
            }
        }

        return binding.root
    }

    private fun onComplete(id: Int) {
        viewModel.completeTask(requireContext().authToken!!, id)
    }

    private fun onDelete(id: Int) {
        viewModel.deleteTask(requireContext().authToken!!, id)
    }

    private fun onUpdate(name: String, id: Int) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToEnterNameDialog(name, id)
        findNavController().navigate(action)
    }
}