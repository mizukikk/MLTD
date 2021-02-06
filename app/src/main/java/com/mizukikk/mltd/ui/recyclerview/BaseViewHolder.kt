package com.mizukikk.mltd.ui.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding> private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    constructor(binding: ViewDataBinding) : this(binding.root) {
        _binding = binding as B
    }

    protected fun getString(id: Int) = itemView.context.getString(id)

}