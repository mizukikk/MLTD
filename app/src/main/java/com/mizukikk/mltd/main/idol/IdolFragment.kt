package com.mizukikk.mltd.main.idol

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialContainerTransform
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolBinding
import com.mizukikk.mltd.main.BaseMainFragment
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
        sharedElementEnterTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun viewModelClass() = IdolViewModel::class.java

    override fun initBinding(view: View) = FragmentIdolBinding.bind(view)

    override fun init() {
        ViewCompat.setTransitionName(binding.root, data.idol.resourceId)
        Glide.with(binding.root)
            .load(data.iconUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.ivTest)
    }

}