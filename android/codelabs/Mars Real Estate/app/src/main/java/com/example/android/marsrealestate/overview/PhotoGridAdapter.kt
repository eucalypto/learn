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

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty


class PhotoGridAdapter(private val onItemClicked: (MarsProperty) -> Unit) :
    ListAdapter<MarsProperty, PropertyViewHolder>(MarsPropertyItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PropertyViewHolder {
        return PropertyViewHolder.from(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bindTo(marsProperty)
    }

}

class MarsPropertyItemCallback : DiffUtil.ItemCallback<MarsProperty>() {

    override fun areItemsTheSame(
        oldItem: MarsProperty,
        newItem: MarsProperty
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MarsProperty,
        newItem: MarsProperty
    ): Boolean {
        return oldItem == newItem
    }

}


class PropertyViewHolder
private constructor(
    private val binding: GridViewItemBinding,
    private val onItemClicked: (MarsProperty) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(marsProperty: MarsProperty) {
        binding.marsProperty = marsProperty
        binding.marsImage.setOnClickListener {
            onItemClicked(marsProperty)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: (MarsProperty) -> Unit
        ): PropertyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = GridViewItemBinding.inflate(inflater, parent, false)
            return PropertyViewHolder(binding, onClick)
        }
    }

}