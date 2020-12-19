package com.mizukikk.mltd.main.idol

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.transition.MaterialContainerTransform
import com.mizukikk.mltd.R
import com.mizukikk.mltd.data.model.IdolField
import com.mizukikk.mltd.databinding.FragmentIdolBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.model.IdolViewModel
import com.mizukikk.mltd.room.query.IdolItem


class IdolFragment :
    BaseMainFragment<IdolViewModel, FragmentIdolBinding>(R.layout.fragment_idol) {

    companion object {
        private val TAG = IdolFragment::class.java.simpleName
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
        setListener()
    }

    private fun setListener() {
        binding.title.swAwakened.setOnCheckedChangeListener { buttonView, isChecked ->
            setIdolData(isChecked)
        }
        binding.ivCard.setOnClickListener {
            val photoUri = if (binding.title.swAwakened.isChecked) {
                if (data.idol.rarity == IdolField.Rarity.SSR) {
                    Uri.parse(data.idol.cardBGAwakenedUrl)
                } else {
                    Uri.parse(data.idol.cardAwakenedUrl)
                }
            } else {
                if (data.idol.rarity == IdolField.Rarity.SSR) {
                    Uri.parse(data.idol.cardBGUrl)
                } else {
                    Uri.parse(data.idol.cardUrl)
                }
            }
            parentActivity?.showPhoto(binding.ivCard, photoUri, data)
        }
    }

    private fun initView() {
        setDefaultData()
        setIdolData(false)
    }

    private fun setDefaultData() {
        binding.title.data = data
        binding.status.data = data
        binding.status.langJp = viewModel.langJp
        binding.costume.costumeUrl = data.costumeEntity?.costumeUrl
        binding.costume.bonusCostumeUrl = data.bonusCostumeEntity?.costumeUrl
        binding.costume.rank5CostumeUrl = data.rank5CostumeEntity?.costumeUrl
    }

    private fun setTransactionAnimation() {
        ViewCompat.setTransitionName(binding.root, data.idol.resourceId)
        startPostponedEnterTransition()
    }

    private fun setIdolData(awakened: Boolean) {
        binding.status.statusData = data.getStatusData(awakened)
        if (awakened) {
            binding.cardBGUrl = data.idol.cardWithSignedAwakenedUrl
            binding.title.iconUrl = data.idol.iconAwakenedUrl
        } else {
            binding.cardBGUrl = data.idol.cardWithSignedUrl
            binding.title.iconUrl = data.idol.iconUrl
        }
    }

}