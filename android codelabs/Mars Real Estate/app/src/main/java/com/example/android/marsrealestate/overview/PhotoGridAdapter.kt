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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty


class PhotoGridAdapter(private val marsProperties: List<MarsProperty>) :
    RecyclerView.Adapter<PropertyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PropertyViewHolder {
        return PropertyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val marsProperty = marsProperties[position]
        holder.bindTo(marsProperty)
    }

    override fun getItemCount(): Int {
        return marsProperties.size
    }

}


class PropertyViewHolder
private constructor(private val binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(marsProperty: MarsProperty) {
        binding.marsProperty = marsProperty
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): PropertyViewHolder {
            val inflater = layoutInflaterFrom(parent)
            val binding = GridViewItemBinding.inflate(inflater, parent, false)
            return PropertyViewHolder(binding)
        }

        private fun layoutInflaterFrom(parent: ViewGroup): LayoutInflater {
            return parent.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

}