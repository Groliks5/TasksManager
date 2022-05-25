package com.groliks.tasksmanager.view.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.groliks.tasksmanager.appComponent
import com.groliks.tasksmanager.authToken
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.databinding.FragmentAuthenticationBinding
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AuthenticationFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AuthenticationViewModel.Factory
    private val viewModel: AuthenticationViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding
        get() = _binding!!

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener { register() }

        binding.login.setOnClickListener { login() }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.registrationResponse.collect { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> {
                        login()
                    }
                    is RequestResult.Error -> {
                        Toast.makeText(requireContext(), requestResult.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authenticationResponse.collect { requestResult ->
                when (requestResult) {
                    is RequestResult.Success -> {
                        requireContext().authToken =
                            requestResult.data?.token ?: requireContext().authToken
                        findNavController().popBackStack()
                    }
                    is RequestResult.Error -> {
                        Toast.makeText(requireContext(), requestResult.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun login() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        viewModel.login(username, password)
    }

    private fun register() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        viewModel.register(username, password)
    }
}