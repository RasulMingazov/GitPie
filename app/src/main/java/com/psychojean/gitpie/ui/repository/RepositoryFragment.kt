package com.psychojean.gitpie.ui.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.psychojean.gitpie.data.enteties.Branch
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.databinding.FragmentRepositoriesBinding
import com.psychojean.gitpie.databinding.FragmentRepositoryBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.extensions.openShareIntent
import com.psychojean.gitpie.ui.repositories.RepositoriesAdapter
import com.psychojean.gitpie.ui.repositories.RepositoriesFragmentDirections
import com.psychojean.gitpie.ui.repositories.RepositoriesViewModel
import com.psychojean.gitpie.ui.repository.branch.BranchesBottomSheet
import com.psychojean.gitpie.ui.settings.theme.ThemeBottomSheet
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repository.*

@AndroidEntryPoint
class RepositoryFragment : Fragment(), BranchesBottomSheet.BranchChangeListener {

    private var binding: FragmentRepositoryBinding by autoCleared()
    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var htmlUrl: String
    private lateinit var branches: ArrayList<Branch>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().getAccessToken().let {
            viewModel.start(
                Triple(
                    it.addToken(),
                    requireArguments().getString("owner"),
                    requireArguments().getString("repo")
                ) as Triple<String, String, String>
            )
        }
        binding.share.setOnClickListener {
            requireActivity().openShareIntent(htmlUrl)
        }

        binding.login.setOnClickListener{
//            findNavController().navigate(
//                RepositoryFragmentDirections.repo(binding.login.text)
//            )
        }

        binding.branchBlock.setOnClickListener {
            val fragment = BranchesBottomSheet()
            val bundle = Bundle()
            bundle.putSerializable("branches", branches)
            fragment.arguments = bundle
            fragment.show(childFragmentManager, "branches")
        }

        observeRepository()
        observeBranches()
    }

    private fun observeRepository() {
        viewModel.repository.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindRepository(it.data!!)
                    progress_bar.visibility = View.GONE
                    container.visibility = View.VISIBLE
                    Log.d("RepositoryFragment", "Repository upload success")
                }
                Resource.Status.ERROR ->
                    Log.d("RepositoryFragment", "Mistake while Repository upload")
                Resource.Status.LOADING ->
                    Log.d("RepositoryFragment", "Repository loading")
            }
        })
    }
    private fun observeBranches() {
            viewModel.branches.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        branches = (it.data as ArrayList<Branch>?)!!
                        Log.d("RepositoryFragment", "Branches upload success")
                    }
                    Resource.Status.ERROR ->
                        Log.d("RepositoryFragment", "Mistake while Branches upload")
                    Resource.Status.LOADING ->
                        Log.d("RepositoryFragment", "Branches loading")
                }
            })
        }

    private fun bindRepository(repository: Repo) {
        binding.repository.text = repository.name
        binding.login.text = repository.owner.login
        binding.forksCount.text = repository.forksCount.toString()
        binding.starsCount.text = repository.starredCount.toString()
        binding.branchName.text = repository.defaultBranch
        if (repository.description != null) {
            binding.description.text = repository.description
        }
        htmlUrl = repository.html
        Glide.with(this)
            .load(repository.owner.avatar)
            .transform(CircleCrop())
            .skipMemoryCache(true)
            .into(binding.avatar)
        binding.watchersCount.text = repository.watchersCount.toString()
        binding.contributorsCount.text = repository.subscribersCount.toString()
        binding.pullRequestsCount.text = repository.reposCount.toString()
    }

    override fun onBranchChanges(text: String) {
        binding.branchName.text = text
    }
}

