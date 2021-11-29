package com.psychojean.gitpie.ui.profile.popular

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.databinding.ItemRepoHorizontalBinding
import com.psychojean.gitpie.extensions.getLanguagesHashMap

class PopularRepositoriesAdapter(private val listener: PopularItemListener , private val context: Context) : RecyclerView.Adapter<PopularRepositoryViewHolder>() {

    interface PopularItemListener {
        fun onClickedRepository(owner: String, repoName: String)
    }

    private val items = ArrayList<Repo>()

    fun setItems(items: List<Repo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRepositoryViewHolder {
        val binding: ItemRepoHorizontalBinding = ItemRepoHorizontalBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return PopularRepositoryViewHolder(binding, listener, context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PopularRepositoryViewHolder, position: Int) = holder.bind(items[position])
}

class PopularRepositoryViewHolder(
    private val itemBinding: ItemRepoHorizontalBinding,
    private val listener: PopularRepositoriesAdapter.PopularItemListener,
    private val context: Context
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var repository: Repo

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Repo) {
        this.repository = item
        itemBinding.login.text = item.owner.login
        itemBinding.repoName.text = item.name
        itemBinding.starredCount.text = item.starredCount.toString()
        itemBinding.language.text = item.language
        Glide.with(itemBinding.root)
            .load(item.owner.avatar)
            .centerCrop()
            .skipMemoryCache(true)
            .into(itemBinding.avatar)


        val hashMap = getLanguagesHashMap()
        if (hashMap[item.language] != null) {
            Glide.with(itemBinding.root)
                .load(
                    ColorDrawable(Color.parseColor(hashMap[item.language]))
                )
                .into(itemBinding.languageColor)
        }

        if (item.language == null) {
            itemBinding.language.visibility = View.GONE
            itemBinding.languageColor.visibility = View.GONE
            }
        }

    override fun onClick(v: View?) {
        listener.onClickedRepository(repository.owner.login, repository.name)
    }
}