package com.technical_challenge.github.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.technical_challenge.github.android_utils.di.ViewModelFactory
import com.technical_challenge.github.databinding.FragmentGithubRepositoryDetailBinding
import com.technical_challenge.github.ui.di.GithubProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubRepositoryDetailFragment : Fragment() {

    private val viewModel by viewModels<GithubRepositoryDetailViewModel> { viewModelFactory }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentGithubRepositoryDetailBinding

    private val args by navArgs<GithubRepositoryDetailFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as GithubProvider)
            .provideGithubComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGithubRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataBindingVariables()
        attachObservers()

        val githubRepository = args.githubRepository

        Glide.with(this@GithubRepositoryDetailFragment)
            .load("https://play-lh.googleusercontent.com/PCpXdqvUWfCW1mXhH1Y_98yBpgsWxuTSTofy3NGMo9yBTATDyzVkqU580bfSln50bFU")
            .into(binding.imageView)

        lifecycleScope.launch {
            viewModel.loadGithubRepository(githubRepository)
        }

        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onSaveButtonClick()
            }
        }
    }

    private fun attachObservers() {
        attachEventObservers()
    }

    private fun attachEventObservers() {
        lifecycleScope.launch {
            viewModel.event.event.collect { event ->
                when (event) {
                    is GithubRepositoryDetailEvent.Event.SuccessWhenUpdatingGithubRepositoryData -> {
                        Toast.makeText(context, "Success when updating github repository info", Toast.LENGTH_LONG).show()
                    }
                    is GithubRepositoryDetailEvent.Event.ErrorWhenUpdatingGithubRepositoryData -> {
                        Toast.makeText(context, "Error when updating github repository info", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setDataBindingVariables() {
        binding.viewModel = viewModel
        binding.data = viewModel.data
        binding.lifecycleOwner = this
    }

}