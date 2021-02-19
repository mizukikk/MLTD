package com.mizukikk.mltd.main.event.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mizukikk.mltd.databinding.DialogAnivIdolListBinding
import com.mizukikk.mltd.main.event.adapter.AnivIdolAdapter
import com.mizukikk.mltd.room.query.IdolItem

class AnivIdolListDialog constructor(private val fm: FragmentManager) : DialogFragment() {

    private var listener: ((Int) -> Unit)? = null
    private lateinit var anivIdolList: List<IdolItem>
    private lateinit var binding: DialogAnivIdolListBinding
    private val anivIdolAdapter by lazy { AnivIdolAdapter() }
    private var isShow = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: NullPointerException) {
        }
        return dialog
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShow = false
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        isShow = false
    }

    fun show(anivIdolList: List<IdolItem>) {
        this.anivIdolList = anivIdolList
        if (isShow.not()) {
            isShow = true
            show(fm, AnivIdolListDialog::class.java.simpleName)
        }
    }

    fun close() {
        if (isShow) {
            isShow = false
            dismiss()
        }
    }

}