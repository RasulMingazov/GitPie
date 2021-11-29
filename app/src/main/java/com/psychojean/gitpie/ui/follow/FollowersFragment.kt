package com.psychojean.gitpie.ui.follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychojean.gitpie.databinding.FragmentFollowersBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment: Fragment(), FollowAdapter.FollowItemListener {
    private val viewModel: FollowViewModel by viewModels()
    private var binding: FragmentFollowersBinding by autoCleared()
    private lateinit var adapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().getAccessToken().let {
            viewModel.start( Pair(
                it.addToken(),
                requireArguments().getString("owner"),
            ) as Pair<String, String>)
        }
        setupRecyclerView()

        viewModel.followers.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.followersRv.visibility = View.VISIBLE
                    adapter.setItems(it.data!!)
                    Log.d("FollowersFragment", "Followers upload success")
                }
                Resource.Status.ERROR -> {
                    Log.d("FollowersFragment", "Mistake while Followers upload")
                }
                Resource.Status.LOADING -> {
                    Log.d("FollowersFragment", "Followers loading")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = FollowAdapter(this)
        binding.followersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.followersRv.adapter = adapter
    }

    override fun onClickedFollow(login: String) {

    }
}