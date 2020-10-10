package com.mizukikk.mltd.main.idol

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialContainerTransform
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.adapter.BannerAdapter
import com.mizukikk.mltd.main.idol.model.IdolViewModel
import com.mizukikk.mltd.room.query.IdolItem


class IdolFragment :
    BaseMainFragment<IdolViewModel, FragmentIdolBinding>(R.layout.fragment_idol) {

    companion object {
        private const val IDOL_DATA = "idolData"

        @JvmStatic
        fun newInstance(idolItem: IdolItem) =
            IdolFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IDOL_DATA, idolItem)
                }
            }
    }

    private lateinit var data: IdolItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable(IDOL_DATA)!!
        }
        postponeEnterTransition()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500
        }
    }

    override fun viewModelClass() = IdolViewModel::class.java

    override fun initBinding(view: View) = FragmentIdolBinding.bind(view)

    override fun init() {
        setTransactionAnimation()
        initView()
    }

    private fun initView() {
        setBanner()
    }

    private fun setBanner() {
        binding.vpCardBanner.adapter = BannerAdapter(data.idol)
    }

    private fun setTransactionAnimation() {
        ViewCompat.setTransitionName(binding.root, data.idol.resourceId)
        startPostponedEnterTransition()
    }

}