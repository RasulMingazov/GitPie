package com.psychojean.gitpie.ui.authtorization

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.psychojean.gitpie.BuildConfig
import com.psychojean.gitpie.R
import com.psychojean.gitpie.extensions.addAccessTokenIntoASharedPreferences
import com.psychojean.gitpie.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_authorization.*


@AndroidEntryPoint
class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    private val viewModel: AuthorizationViewModel by viewModels()

    private val githubUrl = "https://github.com/login/oauth/authorize/" +
            "?client_id=${BuildConfig.CLIENT_ID}" +
            "&scope=repo%20delete_repo" +
            "&redirect_url=${BuildConfig.REDIRECT_URL}"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebViewSettings()
        setupObserver()

        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }

    private fun setupObserver() {
        viewModel.accessToken.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    requireActivity().addAccessTokenIntoASharedPreferences((it.data?.accessToken!!))
                    progress_bar.visibility = View.GONE
                    findNavController().navigate(
                        AuthorizationFragmentDirections.authorizationFragmentToProfileFragment()
                    )
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Check connection", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewSettings() {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            web_view.settings.forceDark = WebSettings.FORCE_DARK_ON
        }
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(githubUrl)
        web_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                progress_bar.visibility = View.VISIBLE
                web_view.visibility = View.GONE
                Toast.makeText(requireContext(), "Connection mistake detected", Toast.LENGTH_SHORT).show()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_bar.visibility = View.GONE
                web_view.visibility = View.VISIBLE
                if (url?.contains("code") == true) {
                    progress_bar.visibility = View.VISIBLE
                    web_view.visibility = View.GONE
                    viewModel.start(url.split("code=")[1])
                }
            }
        }
    }
}