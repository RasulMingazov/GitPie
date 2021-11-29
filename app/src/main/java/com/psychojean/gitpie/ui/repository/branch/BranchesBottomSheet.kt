package com.psychojean.gitpie.ui.repository.branch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.psychojean.gitpie.R
import com.psychojean.gitpie.data.enteties.Branch
import kotlinx.android.synthetic.main.bottom_sheet_branches.*
import java.lang.ClassCastException

class BranchesBottomSheet: BottomSheetDialogFragment(), BranchesAdapter.BranchItemListener {

    private lateinit var adapter: BranchesAdapter

    interface BranchChangeListener {
        fun onBranchChanges(text: String)
    }

    var branchChangeListener: BranchChangeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            branchChangeListener = parentFragment as BranchChangeListener
        } catch (e: ClassCastException) {
            Log.d("BranchesBottomSheet", e.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        branchChangeListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.bottom_sheet_branches, container,
            false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = requireArguments().getSerializable("branches") as ArrayList<Branch>
        setupRecyclerView()
        adapter.setItems(list)
    }

    private fun setupRecyclerView() {
        adapter = BranchesAdapter(this)
        branches_rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        branches_rv.adapter = adapter
    }

    override fun onClickedBranch(name: String) {
        Log.d("BranchesBottomSheet", "clicked '$name''")
        branchChangeListener?.onBranchChanges(name)
        dismiss()
    }
}