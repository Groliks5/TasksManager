package com.groliks.tasksmanager.view.taskslists

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.groliks.tasksmanager.appComponent
import com.groliks.tasksmanager.authToken
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.databinding.FragmentListBinding
import com.groliks.tasksmanager.view.util.EnterNameDialog
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class TasksListsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: TasksListsViewModel.Factory
    private val viewModel: TasksListsViewModel by viewModels { viewModelFactory }

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

        if (requireContext().authToken == null) {
            val action =
                TasksListsFragmentDirections.actionTasksListsFragmentToAuthenticationFragment()
            findNavController().navigate(action)
        } else {
            viewModel.loadTasksLists(requireContext().authToken!!)
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TasksListsAdapter(
            this::onDelete,
            this::onSelect,
            this::onUpdate
        )
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksLists.collect { tasksLists ->
                adapter.submitList(tasksLists)
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
            val action = TasksListsFragmentDirections.actionTasksListsFragmentToEnterNameDialog("")
            findNavController().navigate(action)
        }

        setFragmentResultListener(EnterNameDialog.NEW_NAME_KEY) { _, result ->
            val id = result.getInt(EnterNameDialog.ID_KEY)
            val name = result.getString(EnterNameDialog.NAME_KEY)
            if (id == EnterNameDialog.NEW_NAME_ID) {
                viewModel.createList(requireContext().authToken!!, name!!)
            } else {
                viewModel.updateList(requireContext().authToken!!, name!!, id)
            }
        }

        return binding.root
    }

    private fun onSelect(id: Int) {
        val action = TasksListsFragmentDirections.actionTasksListsFragmentToTaskListFragment(id)
        findNavController().navigate(action)
    }

    private fun onDelete(id: Int) {
        viewModel.deleteList(requireContext().authToken!!, id)
    }

    private fun onUpdate(name: String, id: Int) {
        val action = TasksListsFragmentDirections.actionTasksListsFragmentToEnterNameDialog(name, id)
        findNavController().navigate(action)
    }
}

