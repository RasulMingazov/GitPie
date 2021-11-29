package com.psychojean.gitpie.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.psychojean.gitpie.data.enteties.Organization
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.data.enteties.User
import com.psychojean.gitpie.databinding.FragmentProfileBinding
import com.psychojean.gitpie.extensions.addToken
import com.psychojean.gitpie.extensions.getAccessToken
import com.psychojean.gitpie.extensions.openShareIntent
import com.psychojean.gitpie.ui.MainActivity
import com.psychojean.gitpie.ui.profile.popular.PopularRepositoriesAdapter
import com.psychojean.gitpie.utils.Resource
import com.psychojean.gitpie.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment : Fragment(),
    PopularRepositoriesAdapter.PopularItemListener {

    private val viewModel: ProfileViewModel by viewModels()
    private var binding: FragmentProfileBinding by autoCleared()

    private lateinit var htmlUrl: String
    private lateinit var adapter: PopularRepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).showBottomNav()

        requireActivity().getAccessToken().let {
            Log.d("ProfileFragment", it)
            viewModel.start(it.addToken())
        }
        setupRecyclerView()
        setupObservers()
        setClickedActions()
    }

    private fun setupRecyclerView() {
        adapter = PopularRepositoriesAdapter(this, requireContext())
        popular_repositories_rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popular_repositories_rv.adapter = adapter
    }

    private fun setClickedActions() {
        binding.following.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.profileFragmentToFollowingFragment(binding.login.text.toString())
            )
        }

        binding.followers.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.profileFragmentToFollowersFragment(binding.login.text.toString())
            )
        }
        binding.share.setOnClickListener {
            requireActivity().openShareIntent(htmlUrl)
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.profileFragmentToSettingsFragment()
            )
        }

        binding.repositoriesBlock.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.profileFragmentToRepositoriesFragment()
            )
        }

        binding.starredBlock.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.profileFragmentToStarredFragment()
            )
        }
    }

    private fun setupObservers() {
        setupOrganizationsObserver()
        setupRepositoriesObserver()
        setupStarredObserver()
        setupUserObserver()
    }

    private fun setupRepositoriesObserver() {
        viewModel.repositories.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data!!.also { repos ->
                        bindRepositories(repos)
                        repos.filter { repo -> repo.starredCount > 0 }
                            .sortedWith(
                                compareByDescending { repo ->
                                    repo.starredCount
                                }).let { sortedRepos ->
                                if (sortedRepos.isNotEmpty()) {
                                    adapter.setItems(sortedRepos)
                                    binding.popularRepositoriesBlock.visibility = View.VISIBLE
                                }
                        }
                    }
                    Log.d("ProfileFragment", "Repositories upload success")
                }
                Resource.Status.ERROR ->
                    Log.d("ProfileFragment", "Mistake while repositories upload")
                Resource.Status.LOADING ->
                    Log.d("ProfileFragment", "Repositories loading")
            }
        })
    }

    private fun setupUserObserver() {
        viewModel.user.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindUser(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.container.visibility = View.VISIBLE
                    Log.d("ProfileFragment", "User upload success")
                }
                Resource.Status.ERROR ->
                    Log.d("ProfileFragment", "Mistake while user upload")
                Resource.Status.LOADING ->
                    Log.d("ProfileFragment", "User loading")
            }
        })
    }

    private fun setupStarredObserver() {
        viewModel.starred.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindStarred(it.data!!)
                    Log.d("ProfileFragment", "Starred upload success")
                }
                Resource.Status.ERROR ->
                    Log.d("ProfileFragment", "Mistake while starred upload")
                Resource.Status.LOADING ->
                    Log.d("ProfileFragment", "Starred loading")
            }
        })
    }

    private fun setupOrganizationsObserver() {
        viewModel.organizations.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindOrganizations(it.data!!)
                    Log.d("ProfileFragment", "Organization upload success")
                }
                Resource.Status.ERROR ->
                    Log.d("ProfileFragment", "Mistake while organizations upload")
                Resource.Status.LOADING ->
                    Log.d("ProfileFragment", "Organizations loading")
            }
        })
    }

    private fun bindOrganizations(organizations: List<Organization>) {
        binding.organizationsCount.text = organizations.size.toString()
    }

    private fun bindStarred(starred: List<Repo>) {
        binding.starredCount.text = starred.size.toString()
    }

    private fun bindUser(user: User) {
        binding.login.text = user.login
        binding.followersCount.text = user.followers.toString()
        binding.followingCount.text = user.following.toString()
        htmlUrl = user.html
        Glide.with(this)
            .load(user.avatar)
            .transform(CircleCrop())
            .skipMemoryCache(true)
            .into(binding.avatar)
    }

    private fun bindRepositories(repositories: List<Repo>) {
        binding.repositoriesCount.text = repositories.size.toString()
    }

    override fun onClickedRepository(owner: String, repoName: String) {
        findNavController().navigate(
            ProfileFragmentDirections.profileFragmentToRepositoryFragment(repoName, owner)
        )
    }
}