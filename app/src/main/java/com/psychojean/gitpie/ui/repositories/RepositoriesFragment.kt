package com.psychojean.gitpie.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychojean.gitpie.databinding.FragmentRepositoriesBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.ui.profile.ProfileFragmentDirections
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment : Fragment(), RepositoriesAdapter.RepositoryItemListener {

    private val viewModel: RepositoriesViewModel by viewModels()
    private var binding: FragmentRepositoriesBinding by autoCleared()
    private lateinit var adapter: RepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().getAccessToken().let {
            viewModel.start(it.addToken())
        }
        setupRecyclerView()

        viewModel.repositories.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.repositoriesRv.visibility = View.VISIBLE
                    adapter.setItems(it.data!!)
                    Log.d("RepositoriesFragment", "Repositories upload success")
                }
                Resource.Status.ERROR -> {
                    Log.d("RepositoriesFragment", "Mistake while repositories upload")
                }
                Resource.Status.LOADING -> {
                    Log.d("RepositoriesFragment", "Repositories loading")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = RepositoriesAdapter(this)
        binding.repositoriesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.repositoriesRv.adapter = adapter
    }

    override fun onClickedRepository(owner: String, repoName: String) {
        findNavController().navigate(
            RepositoriesFragmentDirections.repositoriesFragmentToRepositoryFragment(repoName, owner)
        )
    }
}