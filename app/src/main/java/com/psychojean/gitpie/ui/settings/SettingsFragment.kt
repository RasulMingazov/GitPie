package com.psychojean.gitpie.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.psychojean.gitpie.R
import com.psychojean.gitpie.extensions.getNightMode
import com.psychojean.gitpie.extensions.removeAllAccessTokensFromSharedPreferences
import com.psychojean.gitpie.ui.settings.theme.ThemeBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings), ThemeBottomSheet.ThemeChangeListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theme_value.text = requireActivity().getNightMode()

        theme_block.setOnClickListener {
            val fragment = ThemeBottomSheet()
            fragment.show(childFragmentManager, "theme")
        }

        sign_out_block.setOnClickListener {
            requireActivity().removeAllAccessTokensFromSharedPreferences()
            findNavController().navigate(
                SettingsFragmentDirections.settingsFragmentToSignInFragment()
            )
        }
    }

    override fun onThemeChanges(text: String) {
        theme_value.text = text
    }
}