package com.mizukikk.mltd.main.idol

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.adapter.IdolAdapter
import com.mizukikk.mltd.main.idol.model.IdolListResult
import com.mizukikk.mltd.main.idol.model.IdolListViewModel

class IdolListFragment :
    BaseMainFragment<IdolListViewModel, FragmentIdolListBinding>(R.layout.fragment_idol_list) {

    companion object {
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
        binding.showList = false
        binding.load.loading = true
        viewModel.checkDBdData()
    }

    private fun initView() {
        initIdolList()
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
        })
    }
}
