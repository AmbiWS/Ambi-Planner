package com.ambiws.ambiplanner.base.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultListDiffer<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is ItemModel && newItem is ItemModel) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
