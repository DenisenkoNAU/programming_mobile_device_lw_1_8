package com.nau.pmd.lwapp.ui.lab4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.nau.pmd.lwapp.R
import com.nau.pmd.lwapp.databinding.FragmentLab4Binding

class Lab4Fragment : Fragment() {
    private var _binding: FragmentLab4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLab4Binding.inflate(inflater, container, false)

        with(binding) {
            buttonRelativeApply.setOnClickListener { setupRelativeLayoutLogic() }
            buttonConstraintLayoutApply.setOnClickListener { setupConstraintSetLogic() }
        }

        return binding.root
    }

    private fun setupRelativeLayoutLogic() {
        val relativeLayout = binding.relativeLayoutExample
        val cancelButton = binding.buttonCancel
        relativeLayout.removeView(cancelButton)

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.BELOW, R.id.buttonSubmit)
            addRule(RelativeLayout.CENTER_HORIZONTAL)
            topMargin = 32
        }

        relativeLayout.addView(cancelButton, params)
    }

    private fun setupConstraintSetLogic() {
        val layout = binding.root.findViewById<ConstraintLayout>(R.id.constraintLayoutExample)

        val set = ConstraintSet()
        set.clone(layout)

        // Ланцюг між двома кнопками
        set.createHorizontalChain(
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
            intArrayOf(R.id.ctSend, R.id.ctCancel),
            null,
            ConstraintSet.CHAIN_SPREAD
        )

        // Прив’язка заголовка по колу навколо кнопки
        set.constrainCircle(R.id.ctCancel, R.id.ctTitle, 160, 90f)

        set.applyTo(layout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}