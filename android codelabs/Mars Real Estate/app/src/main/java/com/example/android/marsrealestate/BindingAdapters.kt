/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.NetworkStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(view)
    }
}


@BindingAdapter("marsPropertyList")
fun bindMarsPropertyList(
    recycler: RecyclerView,
    marsProperties: List<MarsProperty>?
) {
    (recycler.adapter as PhotoGridAdapter).submitList(marsProperties)
}

@BindingAdapter("networkStatus")
fun bindNetworkStatus(imageView: ImageView, networkStatus: NetworkStatus?) {
    if (networkStatus == null) return
    when (networkStatus) {
        NetworkStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
        }
        NetworkStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }
        NetworkStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("price")
fun bindPrice(textView: TextView, price: Double?) {
    if (price == null) return

    val priceString = "$${price}"
    textView.text = priceString
}