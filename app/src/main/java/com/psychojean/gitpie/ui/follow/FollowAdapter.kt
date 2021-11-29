package com.psychojean.gitpie.ui.follow

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.psychojean.gitpie.data.enteties.Follow
import com.psychojean.gitpie.databinding.ItemFollowBinding

class FollowAdapter(private val listener: FollowItemListener) : RecyclerView.Adapter<FollowViewHolder>() {

    interface FollowItemListener {
        fun onClickedFollow(login: String)
    }

    private val items = ArrayList<Follow>()

    fun setItems(items: List<Follow>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding: ItemFollowBinding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) = holder.bind(items[position])
}

class FollowViewHolder(private val itemBinding: ItemFollowBinding, private val listener: FollowAdapter.FollowItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var item: Follow

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Follow) {
        this.item = item
        itemBinding.login.text = item.login
        Glide.with(itemBinding.root)
            .load(item.avatar)
            .transform(CircleCrop())
            .skipMemoryCache(true)
            .into(itemBinding.avatar)
    }

    override fun onClick(v: View?) {
        listener.onClickedFollow(item.login)
    }
}