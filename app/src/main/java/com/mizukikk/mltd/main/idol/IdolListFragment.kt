package com.mizukikk.mltd.main.idol

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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
            recoverySearchStatus()
        })
    }

    private fun recoverySearchStatus() {
        if (binding.edSearch.text.toString().isNotEmpty()) {
            idolAdapter?.search(binding.edSearch.text.toString())
        }
    }
}
