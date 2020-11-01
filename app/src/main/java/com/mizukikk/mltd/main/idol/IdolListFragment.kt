package com.mizukikk.mltd.main.idol

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.adapter.FilterIdolAdapter
import com.mizukikk.mltd.main.idol.adapter.IdolAdapter
import com.mizukikk.mltd.main.idol.model.IdolListResult
import com.mizukikk.mltd.main.idol.model.IdolListViewModel

class IdolListFragment :
    BaseMainFragment<IdolListViewModel, FragmentIdolListBinding>(R.layout.fragment_idol_list) {

    companion object {
        private val TAG = IdolListFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = IdolListFragment()
    }

    private var idolAdapter: IdolAdapter? = null

    override fun viewModelClass() = IdolListViewModel::class.java

    override fun initBinding(view: View): FragmentIdolListBinding =
        FragmentIdolListBinding.bind(view)

    override fun init() {
        initView()
        initViewModel()
        setListener()
        getIdolList()
    }

    private fun getIdolList() {
        binding.showList = false
        binding.load.loading = true
        if (viewModel.idolListLiveData.value == null) {
            viewModel.checkDBdData()
        }
    }

    private fun setListener() {
        idolAdapter?.setIdolListListener { shareView, idolItem ->
            parentActivity?.setIdolFragment(shareView, idolItem)
        }
        binding.edSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                idolAdapter?.search(v.text.toString())
            }
            false
        }
        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.cancelEnable = s.toString().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.ivCancel.setOnClickListener {
            binding.edSearch.setText("")
            idolAdapter?.clearSearch()
        }
        binding.ivFilter.setOnClickListener {
            binding.drawableLayout.openDrawer(GravityCompat.END)
        }
        binding.navFilter.tvFilter.setOnClickListener {
            binding.drawableLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun initView() {
        initDrawableLayout()
        initIdolList()
        initFilterList()
    }

    private fun initFilterList() {
        val idolTypeArray = resources.getStringArray(R.array.idolType)
        val rarityArray = resources.getStringArray(R.array.rarity)
        val extraTypeArray = resources.getStringArray(R.array.extraType)
        val skillEffectArray = resources.getStringArray(R.array.skillEffect)
        val centerEffectAttributeArray = resources.getStringArray(R.array.centerEffectAttribute)

        binding.navFilter.rvIdolType.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvCenterEffect.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvExtraType.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvRarity.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvSkill.layoutManager = FlexboxLayoutManager(requireContext())

        binding.navFilter.rvIdolType.adapter = FilterIdolAdapter(idolTypeArray)
        binding.navFilter.rvCenterEffect.adapter = FilterIdolAdapter(centerEffectAttributeArray)
        binding.navFilter.rvExtraType.adapter = FilterIdolAdapter(extraTypeArray)
        binding.navFilter.rvRarity.adapter = FilterIdolAdapter(rarityArray)
        binding.navFilter.rvSkill.adapter = FilterIdolAdapter(skillEffectArray)
    }

    private fun initDrawableLayout() {
        binding.drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun initIdolList() {
        binding.idol.rvIdol.layoutManager = LinearLayoutManager(context)
        idolAdapter = IdolAdapter()
        binding.idol.rvIdol.adapter = idolAdapter
    }

    private fun initViewModel() {
        viewModel.idolListEvent.observe(this, Observer { result ->
            when (result) {
                is IdolListResult.CheckDB -> {
                    if (result.dataEmpty) {
                        binding.showList = false
                        binding.load.loading = true
                        viewModel.downloadAllCard()
                    } else {
                        binding.showList = true
                        viewModel.getFirstListItem()
                    }
                }
                is IdolListResult.Download -> {
                    if (result.success) {
                        binding.showList = false
                        binding.load.loading = false
                        binding.load.progressSave.max = result.totalItem
                    } else {
                        //show fail dialog
                        binding.showList = true
                    }
                }
                is IdolListResult.SaveIdolData -> {
                    binding.showList = false
                    binding.load.loading = false
                    binding.load.tvProgress.text = result.progressText
                    binding.load.progressSave.progress = result.progress
                    if (result.saveSuccess)
                        viewModel.getFirstListItem()
                }
            }
        })
        viewModel.idolListLiveData.observe(this, Observer { idolList ->
            binding.showList = true
            binding.load.loading = false
            idolAdapter?.swapData(idolList)
            recoverySearchStatus()
        })
    }

    private fun recoverySearchStatus() {
        if (binding.edSearch.text.toString().isNotEmpty()) {
            idolAdapter?.search(binding.edSearch.text.toString())
        }
    }
}
