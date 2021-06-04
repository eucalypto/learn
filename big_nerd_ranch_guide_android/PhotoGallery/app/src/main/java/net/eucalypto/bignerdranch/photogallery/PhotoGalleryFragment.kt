package net.eucalypto.bignerdranch.photogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoGalleryBinding

class PhotoGalleryFragment : Fragment() {

    private val viewModel: PhotoGalleryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
            viewModel.galleryItemLiveData.observe(viewLifecycleOwner) { galleryItems ->
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            }
        }


    }

    private class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
        RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoHolder {
            val textView = TextView(parent.context)
            return PhotoHolder(textView)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val title = galleryItems[position].title
            holder.bindTitle(title)
        }

        override fun getItemCount(): Int {
            return galleryItems.size
        }

        private class PhotoHolder(itemTextView: TextView) :
            RecyclerView.ViewHolder(itemTextView) {
            val bindTitle: (CharSequence) -> Unit = itemTextView::setText
        }
    }

}