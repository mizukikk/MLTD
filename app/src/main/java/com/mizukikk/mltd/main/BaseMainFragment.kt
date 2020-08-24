package com.mizukikk.mltd.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mizukikk.mltd.main.model.BaseMainViewModel

abstract class BaseMainFragment<VM : BaseMainViewModel, B : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    Fragment(layoutRes) {


    private lateinit var _viewModel: VM
    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected val viewModel get() = _viewModel

    abstract fun viewModelClass(): Class<VM>
    abstract fun initBinding(view: View): B
    abstract fun init()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        initBinding(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = ViewModelProviders.of(this)
            .get(viewModelClass())
        init()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}