package net.eucalypto.bignerdranch.photogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoGalleryBinding
import timber.log.Timber

class PhotoGalleryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flickrLiveData = FlickrFetcher().fetchPhotos()
        flickrLiveData.observe(this) { galleryItems ->
            Timber.d("Response Received: $galleryItems")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPhotoGalleryBinding.inflate(
            inflater, container, false
        ).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentPhotoGalleryBinding.bind(view)

        with(viewBinding) {
            photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        }
    }

}