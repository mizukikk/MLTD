package com.mizukikk.mltd.main.idol

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.adapter.FilterIdolManager
import com.mizukikk.mltd.main.idol.adapter.IdolAdapter
import com.mizukikk.mltd.main.idol.model.IdolListResult
import com.mizukikk.mltd.main.idol.service.UpdateIdolService
import com.mizukikk.mltd.main.idol.viewmodel.IdolListViewModel
import kotlinx.android.synthetic.main.fragment_idol_list.view.*

class IdolListFragment :
    BaseMainFragment<IdolListViewModel, FragmentIdolListBinding>(R.layout.fragment_idol_list) {

    companion object {
        private val TAG = IdolListFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = IdolListFragment()
    }

    private var idolAdapter: IdolAdapter? = null
    private var filterIdolManager: FilterIdolManager? = null
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                UpdateIdolService.UPDATE_RESULT -> {
                    context?.let {
                        Toast.makeText(
                            it,
                            R.string.toast_update_idol_data_success,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    override fun viewModelClass() = IdolListViewModel::class.java

    override fun initBinding(view: View): FragmentIdolListBinding =
        FragmentIdolListBinding.bind(view)

    override fun init() {
        initView()
        initViewModel()
        setListener()
        getFilterData()
        getIdolList()
    }

    private fun getFilterData() {
        viewModel.getFilterData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = IntentFilter().apply {
            addAction(UpdateIdolService.UPDATE_RESULT)
        }
        context?.registerReceiver(updateReceiver, intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            context?.unregisterReceiver(updateReceiver)
        } catch (e: Exception) {
            //receiver not register
        }
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
        binding.idol.refresh.setOnRefreshListener {
            filterIdolManager?.clearFilter()
            binding.edSearch.edSearch.setText("")
            binding.edSearch.clearFocus()
            viewModel.refreshData()
        }
        binding.edSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                idolAdapter?.search(v.text.toString(), filterIdolManager?.getFilterData())
            }
            false
        }
        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkSearchStatus()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.ivMenu.setOnClickListener {
            parentActivity?.showNavMenu()
        }
        binding.ivCancel.setOnClickListener {
            binding.edSearch.setText("")
            filterIdolManager?.clearFilter()
            idolAdapter?.clearSearch()
            checkSearchStatus()
        }
        binding.ivOpenFilter.setOnClickListener {
            filterIdolManager?.showFilterList()
            binding.drawableLayout.openDrawer(GravityCompat.END)
        }
        binding.navFilter.tvFilterSearch.setOnClickListener {
            idolAdapter?.search(
                binding.edSearch.text.toString(),
                filterIdolManager?.getFilterData()
            )
            checkSearchStatus()
            binding.drawableLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun checkSearchStatus() {
        binding.cancelEnable =
            binding.edSearch.text.toString().isNotEmpty() || filterIdolManager?.isFilter ?: false
    }

    private fun initView() {
        binding.edSearch.isFocusable = false
        initDrawableLayout()
        initIdolList()
        initFilterList()
    }

    private fun initFilterList() {
        binding.navFilter.rvIdolType.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvCenterEffect.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvExtraType.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvRarity.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvSkill.layoutManager = FlexboxLayoutManager(requireContext())
        binding.navFilter.rvSkillDuration.layoutManager = FlexboxLayoutManager(requireContext())
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
                        viewModel.getIdolListItem()
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
                        viewModel.getIdolListItem()
                }
                is IdolListResult.UpdateIdolData -> {
                    if (result.update)
                        context?.let {
                            UpdateIdolService.start(it, result.lastIdolId)
                        }
                }
            }
        })
        viewModel.idolListLiveData.observe(this, Observer { idolList ->
            binding.idol.refresh.isRefreshing = false
            binding.showList = true
            binding.load.loading = false
            idolAdapter?.swapData(idolList)
            recoverySearchStatus()
        })
        viewModel.filterIdolManagerLiveData.observe(this, Observer {
            filterIdolManager = it
            binding.navFilter.rvIdolType.adapter = filterIdolManager?.idolTypeAdapter
            binding.navFilter.rvCenterEffect.adapter = filterIdolManager?.centerEffectAdapter
            binding.navFilter.rvExtraType.adapter = filterIdolManager?.extraTypeAdapter
            binding.navFilter.rvRarity.adapter = filterIdolManager?.rarityAdapter
            binding.navFilter.rvSkill.adapter = filterIdolManager?.skillAdapter
            binding.navFilter.rvSkillDuration.adapter = filterIdolManager?.skillDurationAdapter
        })
    }

    private fun recoverySearchStatus() {
        if (binding.edSearch.text.toString().isNotEmpty() || filterIdolManager?.isFilter == true) {
            idolAdapter?.search(
                binding.edSearch.text.toString(),
                filterIdolManager?.getFilterData()
            )
        }
    }

    fun reloadData() {
        idolAdapter?.swapData(mutableListOf())
        idolAdapter?.clearSearch()
        filterIdolManager?.clearFilter()
        viewModel.reloadData()
    }
}
