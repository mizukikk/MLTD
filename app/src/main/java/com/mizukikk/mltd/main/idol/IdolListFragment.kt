package com.mizukikk.mltd.main.idol

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.model.IdolListViewModel
import com.mizukikk.mltd.main.model.BaseMainViewModel

class IdolListFragment :
    BaseMainFragment<IdolListViewModel, FragmentIdolListBinding>(R.layout.fragment_idol_list) {

    companion object {
        @JvmStatic
        fun newInstance() = IdolListFragment()
    }

    override fun viewModelClass() = IdolListViewModel::class.java

    override fun initBinding(view: View): FragmentIdolListBinding =
        FragmentIdolListBinding.bind(view)

    override fun init() {
        binding.tvTitle.setOnClickListener {
            viewModel.getAllCard()
        }
    }
}
