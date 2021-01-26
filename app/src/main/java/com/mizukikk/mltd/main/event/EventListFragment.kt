package com.mizukikk.mltd.main.event

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.viewmodel.EventListViewModel

class EventListFragment :
    BaseMainFragment<EventListViewModel, FragmentEventListBinding>(R.layout.fragment_event_list) {

    companion object {
        private val TAG = EventListFragment::class.java.simpleName
        fun newInstance() = EventListFragment()
    }

    override fun viewModelClass() = EventListViewModel::class.java

    override fun initBinding(view: View) = FragmentEventListBinding.bind(view)

    override fun init() {
        viewModel.eventListLiveData.observe(this, Observer {
            Log.d(TAG, "init: aaa3 $it")
        })
        viewModel.getEventList()
    }
}