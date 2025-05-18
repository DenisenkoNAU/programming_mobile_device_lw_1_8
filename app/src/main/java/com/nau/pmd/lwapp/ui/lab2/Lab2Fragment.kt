package com.nau.pmd.lwapp.ui.lab2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nau.pmd.lwapp.databinding.FragmentLab2Binding

class Lab2Fragment : Fragment() {
    private var _binding: FragmentLab2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLab2Binding.inflate(inflater, container, false)

        with(binding) {
            btnAdd.setOnClickListener { calculate('+') }
            btnSubtract.setOnClickListener { calculate('-') }
            btnMultiply.setOnClickListener { calculate('*') }
            btnDivide.setOnClickListener { calculate('/') }

            btnClear.setOnClickListener {
                etNumber1.text.clear()
                etNumber2.text.clear()
                tvResult.text = "Результат:"
            }

            btnExit.setOnClickListener {
                requireActivity().finish()
            }
        }

        return binding.root
    }

    private fun calculate(op: Char) {
        val num1 = binding.etNumber1.text.toString().toDoubleOrNull()
        val num2 = binding.etNumber2.text.toString().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(requireContext(), "Введіть обидва числа", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (op) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> if (num2 != 0.0) num1 / num2 else "Ділення на 0"
            else -> "Невідома операція"
        }

        binding.tvResult.text = "Результат: $result"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}