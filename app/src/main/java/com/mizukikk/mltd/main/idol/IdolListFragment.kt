package com.mizukikk.mltd.main.idol

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentIdolListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.idol.model.IdolListResult
import com.mizukikk.mltd.main.idol.model.IdolListViewModel

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
        binding.idol.rvIdol.layoutManager = LinearLayoutManager(context)
        initViewModel()
        binding.showList = false
        binding.load.loading = true
        viewModel.checkDBdData()
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
        viewModel.idolListLiveData.observe(this, Observer {
            Toast.makeText(context, "load success !!", Toast.LENGTH_SHORT).show()
        })
    }
}
