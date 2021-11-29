package com.psychojean.gitpie.ui.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.databinding.ItemRepoHorizontalBinding
import com.psychojean.gitpie.databinding.ItemRepositoryBinding
import com.psychojean.gitpie.extensions.getLanguagesHashMap

class RepositoriesAdapter(private val listener: RepositoryItemListener) : RecyclerView.Adapter<RepositoryViewHolder>() {

    interface RepositoryItemListener {
        fun onClickedRepository(owner: String, repoName: String)
    }

    private val items = ArrayList<Repo>()

    fun setItems(items: List<Repo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding: ItemRepositoryBinding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) = holder.bind(items[position])
}

class RepositoryViewHolder(private val itemBinding: ItemRepositoryBinding, private val listener: RepositoriesAdapter.RepositoryItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var item: Repo

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Repo) {
        this.item = item
        itemBinding.login.text = item.owner.login
        itemBinding.repoName.text = item.name
        Glide.with(itemBinding.root)
            .load(item.owner.avatar)
            .transform(CircleCrop())
            .skipMemoryCache(true)
            .into(itemBinding.avatar)
    }

    override fun onClick(v: View?) {
        listener.onClickedRepository(item.owner.login, item.name)
    }
}

