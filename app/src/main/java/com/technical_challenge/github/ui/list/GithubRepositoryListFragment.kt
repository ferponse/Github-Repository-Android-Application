package com.technical_challenge.github.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.technical_challenge.github.android_utils.di.ViewModelFactory
import com.technical_challenge.github.databinding.FragmentGithubRepositoryListBinding
import com.technical_challenge.github.ui.di.GithubProvider
import com.technical_challenge.github.ui.list.adapter.GithubRepositoryLoadStateAdapter
import com.technical_challenge.github.ui.list.adapter.RepositoryAdapter
import com.technical_challenge.github.ui.list.adapter.SwipeLeftItemTouchHelper
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubRepositoryListFragment : Fragment() {

    private val viewModel by viewModels<GithubRepositoryListViewModel> { viewModelFactory }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentGithubRepositoryListBinding
    private lateinit var adapter: RepositoryAdapter

    private val onItemClickListener = object : RepositoryAdapter.OnItemClickListener {
        override fun onItemClick(githubRepository: GithubRepositoryUIModel) {
            val action =
                GithubRepositoryListFragmentDirections.navigateToGithubRepositoryDetailFragment(githubRepository = githubRepository)
            findNavController().navigate(action)
        }
    }

    private fun onSwipeToLeft(position: Int) {
        val item = adapter.snapshot().items[position]

        lifecycleScope.launch {
            viewModel.deleteGithubRepository(item.id)
        }
    }

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
        binding = FragmentGithubRepositoryListBinding.inflate(inflater, container, false)

        setRecyclerView()

        viewModel.data.githubRepositoryPagingData.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataBindingVariables()
        attachObservers()
    }

    private fun attachObservers() {
        attachEventObservers()
    }

    private fun attachEventObservers() {
        lifecycleScope.launch {
            viewModel.event.event.collect { event ->
                when (event) {
                    is RepositoryListEvent.Event.SuccessWhenDeletingGithubRepository -> {
                        Toast.makeText(context, "Success when deleting item", Toast.LENGTH_LONG)
                            .show()
                    }

                    is RepositoryListEvent.Event.ErrorWhenDeletingGithubRepository -> {
                        Toast.makeText(context, "Error when deleting item", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        adapter = RepositoryAdapter(onItemClickListener)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GithubRepositoryLoadStateAdapter {
                    adapter.retry()
                },
                footer = GithubRepositoryLoadStateAdapter {
                    adapter.retry()
                }
            )
        }
        val itemTouchHelper = ItemTouchHelper(SwipeLeftItemTouchHelper(::onSwipeToLeft))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setDataBindingVariables() {
        binding.data = viewModel.data
        binding.lifecycleOwner = this
    }

}