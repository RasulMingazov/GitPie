package com.psychojean.gitpie.ui.follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychojean.gitpie.databinding.FragmentFollowingBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment: Fragment(), FollowAdapter.FollowItemListener {

    private val viewModel: FollowViewModel by viewModels()
    private var binding: FragmentFollowingBinding by autoCleared()
    private lateinit var adapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
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

        viewModel.following.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.followingRv.visibility = View.VISIBLE
                    adapter.setItems(it.data!!)
                    Log.d("FollowingFragment", "Following upload success")
                }
                Resource.Status.ERROR -> {
                    Log.d("FollowingFragment", "Mistake while Following upload")
                }
                Resource.Status.LOADING -> {
                    Log.d("FollowingFragment", "Following loading")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = FollowAdapter(this)
        binding.followingRv.layoutManager = LinearLayoutManager(requireContext())
        binding.followingRv.adapter = adapter
    }

    override fun onClickedFollow(login: String) {

    }
}