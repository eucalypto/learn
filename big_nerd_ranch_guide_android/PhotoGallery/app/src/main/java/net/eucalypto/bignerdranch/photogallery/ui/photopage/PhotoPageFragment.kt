package net.eucalypto.bignerdranch.photogallery.ui.photopage

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoPageBinding

private const val ARG_URI = "photo_page_url"

class PhotoPageFragment : Fragment() {

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uri = arguments?.getParcelable<Uri>(ARG_URI) ?: Uri.EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            FragmentPhotoPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPhotoPageBinding.bind(view)

        setUpWebView(binding)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView(binding: FragmentPhotoPageBinding) {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(uri.toString())
        }
    }


    companion object {
        fun newInstance(uri: Uri): PhotoPageFragment {
            return PhotoPageFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_URI, uri) }
            }
        }
    }
}