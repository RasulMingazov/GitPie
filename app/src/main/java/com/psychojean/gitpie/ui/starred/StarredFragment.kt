package com.psychojean.gitpie.ui.starred

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychojean.gitpie.databinding.FragmentStarredBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.ui.repositories.RepositoriesFragmentDirections
import com.psychojean.gitpie.ui.repositories.RepositoriesViewModel
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarredFragment: Fragment(), StarredAdapter.StarredItemListener {

    private val viewModel: StarredViewModel by viewModels()
    private var binding: FragmentStarredBinding by autoCleared()
    private lateinit var adapter: StarredAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarredBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().getAccessToken().let {
            viewModel.start(it.addToken())
        }
        setupRecyclerView()

        viewModel.starred.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.starredRv.visibility = View.VISIBLE
                    adapter.setItems(it.data!!)
                    Log.d("StarredFragment", "Repositories upload success")
                }
                Resource.Status.ERROR -> {
                    Log.d("StarredFragment", "Mistake while repositories upload")
                }
                Resource.Status.LOADING -> {
                    Log.d("StarredFragment", "Repositories loading")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = StarredAdapter(this, requireContext())
        binding.starredRv.layoutManager = LinearLayoutManager(requireContext())
        binding.starredRv.adapter = adapter
    }

    override fun onClickedRepository(owner: String, repoName: String) {
        findNavController().navigate(
            StarredFragmentDirections.starredFragmentToRepositoryFragment(repoName, owner)
        )
    }
}