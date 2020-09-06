package com.mizukikk.mltd.ui.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding>(protected val binding: B) :
    RecyclerView.ViewHolder(binding.root)