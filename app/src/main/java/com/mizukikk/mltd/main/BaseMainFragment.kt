package com.mizukikk.mltd.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mizukikk.mltd.main.viewmodel.BaseMainViewModel

abstract class BaseMainFragment<VM : BaseMainViewModel, B : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    Fragment(layoutRes) {


    private lateinit var _viewModel: VM
    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected val viewModel get() = _viewModel
    protected var parentActivity: InteractiveMainActivity? = null

    abstract fun viewModelClass(): Class<VM>
    abstract fun initBinding(view: View): B
    abstract fun init()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InteractiveMainActivity)
            this.parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        _binding = initBinding(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        init()
    }

    private fun initViewModel() {
        _viewModel = ViewModelProviders.of(this)
            .get(viewModelClass())
        viewModel.progressEvent.observe(this, Observer { show ->
            if (show) {
                parentActivity?.showProgressBar()
            } else {
                parentActivity?.dismissProgressBar()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}