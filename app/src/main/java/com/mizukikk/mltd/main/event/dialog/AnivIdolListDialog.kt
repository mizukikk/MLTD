package com.mizukikk.mltd.main.event.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mizukikk.mltd.databinding.DialogAnivIdolListBinding
import com.mizukikk.mltd.main.event.adapter.AnivIdolAdapter
import com.mizukikk.mltd.main.event.adapter.AnivIdolDecoration
import com.mizukikk.mltd.room.query.IdolItem
import java.util.*

class AnivIdolListDialog : DialogFragment() {

    companion object {
        private val TAG = AnivIdolListDialog::class.java.simpleName
        fun newInstance(anivIdolList: List<IdolItem>) = AnivIdolListDialog().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(TAG, anivIdolList as ArrayList<out Parcelable>)
            }
        }
    }

    private var listener: ((Int) -> Unit)? = null
    private lateinit var anivIdolList: List<IdolItem>
    private lateinit var binding: DialogAnivIdolListBinding
    private val anivIdolAdapter by lazy { AnivIdolAdapter() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: NullPointerException) {
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            anivIdolList = it.getParcelableArrayList(TAG)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAnivIdolListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListener()
    }

    private fun initView() {
        binding.rvAnivIdol.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvAnivIdol.adapter = anivIdolAdapter
        binding.rvAnivIdol.addItemDecoration(AnivIdolDecoration(4))
        anivIdolAdapter.swapData(anivIdolList)
    }

    private fun setListener() {
        anivIdolAdapter.setListener { idolId ->
            listener?.invoke(idolId)
            close()
        }
        binding.root.setOnClickListener {
            close()
        }
    }

    fun setAnivIdolListDialogListener(listener: (Int) -> Unit): AnivIdolListDialog {
        this.listener = listener
        return this
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }


    fun show(fm: FragmentManager) {
        fm.executePendingTransactions()
        if (this.isAdded.not())
            show(fm, TAG)
    }

    fun close() {
        dismiss()
    }

}