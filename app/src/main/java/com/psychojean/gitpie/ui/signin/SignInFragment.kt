package com.psychojean.gitpie.ui.signin

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.psychojean.gitpie.ui.MainActivity
import com.psychojean.gitpie.R
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment: Fragment(R.layout.fragment_sign_in) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNav()
        sign_in.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(resources.getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
        sign_in.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToAuthorizationFragment()
            )
        }
    }
}