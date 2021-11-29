package com.psychojean.gitpie.ui.repository.branch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psychojean.gitpie.data.enteties.Branch
import com.psychojean.gitpie.databinding.ItemBranchBinding

class BranchesAdapter(private val listener: BranchItemListener) : RecyclerView.Adapter<BranchViewHolder>() {

    interface BranchItemListener {
        fun onClickedBranch(name: String)
    }

    private val items = ArrayList<Branch>()

    fun setItems(items: List<Branch>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding: ItemBranchBinding = ItemBranchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class BranchViewHolder(private val itemBinding: ItemBranchBinding, private val listener: BranchesAdapter.BranchItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var item: Branch

    init {
        itemBinding.name.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Branch) {
        this.item = item
        itemBinding.name.text = item.name
    }

    override fun onClick(v: View?) {
        listener.onClickedBranch(item.name)
    }}