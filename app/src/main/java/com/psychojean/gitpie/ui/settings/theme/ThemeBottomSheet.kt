package com.psychojean.gitpie.ui.settings.theme

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.psychojean.gitpie.R
import com.psychojean.gitpie.extensions.*
import kotlinx.android.synthetic.main.bottom_sheet_theme.*
import java.lang.ClassCastException

class ThemeBottomSheet: BottomSheetDialogFragment() {

    interface ThemeChangeListener {
        fun onThemeChanges(text: String)
    }
    var themeChangeListener: ThemeChangeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            themeChangeListener = parentFragment as ThemeChangeListener
        } catch (e: ClassCastException) {
            Log.d("ThemeBottomSheet", e.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.bottom_sheet_theme, container,
            false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireActivity().getNightMode()!!) {
            getString(R.string.night) -> night.setTextColor(resources.getColor(R.color.green))
            getString(R.string.light) -> light.setTextColor(resources.getColor(R.color.green))
            getString(R.string.system) -> system.setTextColor(resources.getColor(R.color.green))
        }

        light.setOnClickListener {
            setAllDefault()
            light.setTextColor(resources.getColor(R.color.green))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            getString(R.string.light).also {
                themeChangeListener?.onThemeChanges(it)
                requireActivity().removeAllNightModeFromSharedPreferences()
                requireActivity().addNightModeIntoASharedPreferences(it)
            }
        }

        night.setOnClickListener {
            setAllDefault()
            night.setTextColor(resources.getColor(R.color.green))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            getString(R.string.night).also {
                themeChangeListener?.onThemeChanges(it)
                requireActivity().removeAllNightModeFromSharedPreferences()
                requireActivity().addNightModeIntoASharedPreferences(it)
            }
        }
        system.setOnClickListener {
            setAllDefault()
            system.setTextColor(resources.getColor(R.color.green))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            getString(R.string.system).also {
                themeChangeListener?.onThemeChanges(it)
                requireActivity().removeAllNightModeFromSharedPreferences()
                requireActivity().addNightModeIntoASharedPreferences(it)
            }
        }
    }
    private fun setAllDefault() {
        night.setTextColor(requireContext().getColorResCompat(R.attr.simpleTextColor))
        light.setTextColor(requireContext().getColorResCompat(R.attr.simpleTextColor))
        system.setTextColor(requireContext().getColorResCompat(R.attr.simpleTextColor))
    }
}