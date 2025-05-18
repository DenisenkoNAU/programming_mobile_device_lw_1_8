package com.nau.pmd.lwapp.ui.lab6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.nau.pmd.lwapp.databinding.FragmentLab6Binding
import kotlin.math.sin

class Lab6Fragment : Fragment() {
    private var _binding: FragmentLab6Binding? = null
    private val binding get() = _binding!!

    private val names = listOf("Київ", "Львів", "Одеса", "Харків", "Дніпро")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLab6Binding.inflate(inflater, container, false)

        setupListView()
        setupFunctionGrid()

        return binding.root
    }

    private fun setupListView() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val name = names[position]
            binding.selectedItemText.text = "Вибрано: $name"
            Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFunctionGrid() {
        binding.btnCalculate.setOnClickListener {
            val xStart = binding.etStart.text.toString().toDoubleOrNull()
            val xEnd = binding.etEnd.text.toString().toDoubleOrNull()
            val step = binding.etStep.text.toString().toDoubleOrNull()

            if (xStart == null || xEnd == null || step == null || step <= 0 || xEnd <= xStart) {
                Toast.makeText(requireContext(), "Невірні дані", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val xValues = mutableListOf<Double>()
            val yValues = mutableListOf<Double>()
            var x = xStart
            while (x <= xEnd + 0.0001) {
                xValues.add(x)
                yValues.add(sin(x)) // наприклад, функція y = sin(x)
                x += step
            }

            val table = mutableListOf<String>()
            table.add("X")
            table.add("Y")
            for (i in xValues.indices) {
                table.add("%.2f".format(xValues[i]))
                table.add("%.2f".format(yValues[i]))
            }

            val gridAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, table)
            binding.gridView.adapter = gridAdapter
        }

        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}