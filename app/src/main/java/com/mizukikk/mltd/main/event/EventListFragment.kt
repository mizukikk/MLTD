package com.mizukikk.mltd.main.event

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mizukikk.mltd.R
import com.mizukikk.mltd.databinding.FragmentEventListBinding
import com.mizukikk.mltd.main.BaseMainFragment
import com.mizukikk.mltd.main.event.adapter.EventAdapter
import com.mizukikk.mltd.main.event.viewmodel.EventListViewModel

class EventListFragment :
    BaseMainFragment<EventListViewModel, FragmentEventListBinding>(R.layout.fragment_event_list) {

    companion object {
        private val TAG = EventListFragment::class.java.simpleName
        fun newInstance() = EventListFragment()
    }

    private var eventAdapter: EventAdapter? = null

    override fun viewModelClass() = EventListViewModel::class.java

    override fun initBinding(view: View) = FragmentEventListBinding.bind(view)

    override fun init() {
        initView()
        initViewModel()
        viewModel.getEventList()
    }

    private fun initView() {
        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        viewModel.eventListLiveData.observe(this, Observer {
            binding.progress.visibility = View.GONE
            if (eventAdapter == null) {
                eventAdapter = EventAdapter(it)
            }
            eventAdapter?.setListener { eventData ->
                viewModel.goToEventPage(eventData) { data ->
                    parentActivity?.setEventDetailFragment(data)
                }
            }
            binding.rvEvent.adapter = eventAdapter
        })
    }
}