package com.nau.pmd.lwapp.ui.lab8

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.nau.pmd.lwapp.databinding.FragmentLab8Binding

class Lab8Fragment : Fragment() {
    private var _binding: FragmentLab8Binding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: StaffDbHelper
    private lateinit var staffList: List<Pair<Int, String>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLab8Binding.inflate(inflater, container, false)
        dbHelper = StaffDbHelper(requireContext())

        showAllStaff()

        binding.btnInsert.setOnClickListener {
            dbHelper.insertSampleData()
            showAllStaff()
            Toast.makeText(requireContext(), "Дані додано", Toast.LENGTH_SHORT).show()
        }

        binding.btnQuery.setOnClickListener {
            showAllStaff()
        }

        binding.btnQueryDepartment.setOnClickListener {
            showByDepartmentStaff()
        }

        binding.listViewStaff.setOnItemClickListener { _, _, position, _ ->
            val staffId = staffList[position].first
            dbHelper.deleteById(staffId)
            Toast.makeText(requireContext(), "Видалено", Toast.LENGTH_SHORT).show()
            showAllStaff()
        }

        return binding.root
    }

    private fun showAllStaff() {
        staffList = dbHelper.getAllStaff()
        val names = staffList.map { it.second }
        binding.listViewStaff.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
    }

    private fun showByDepartmentStaff() {
        staffList = dbHelper.getByDepartment(1)
        val names = staffList.map { it.second }
        binding.listViewStaff.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}