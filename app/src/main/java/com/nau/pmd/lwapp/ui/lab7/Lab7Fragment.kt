package com.nau.pmd.lwapp.ui.lab7

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.ActionMode
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.nau.pmd.lwapp.R
import com.nau.pmd.lwapp.databinding.FragmentLab7Binding

class Lab7Fragment : Fragment() {
    private var _binding: FragmentLab7Binding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: ArrayAdapter<String>
    private val listItems = listOf("Пункт 1", "Пункт 2", "Пункт 3", "Пункт 4", "Пункт 5")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLab7Binding.inflate(inflater, container, false)

        // Options Menu
        initOptionsMenu()

        // Context Menu
        registerForContextMenu(binding.myTextView)

        // Сontextual Action Mode
        listAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, listItems)
        binding.myListView.adapter = listAdapter

        initСontextualActionMode()

        return binding.root
    }

    // Options Menu
    private fun initOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_lab7_options, menu)

                // Вмикаємо іконки
                try {
                    if (menu.javaClass.simpleName == "MenuBuilder") {
                        val method = menu.javaClass.getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean::class.java
                        )
                        method.isAccessible = true
                        method.invoke(menu, true)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_popup_trigger -> {
                        showPopupMenu(binding.ivMenuAnchor)
                        return true
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Вибрано: ${menuItem.title}", Toast.LENGTH_SHORT).show();
                        return true
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // Popup menu
    private fun showPopupMenu(anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menuInflater.inflate(R.menu.menu_lab7_popup, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            Toast.makeText(requireContext(), "Сортування: ${item.title}", Toast.LENGTH_SHORT).show()
            true
        }

        popup.show()
    }

    // Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.menu_lab7_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val textView = binding.myTextView
        when (item.itemId) {
            R.id.context_red -> textView.setTextColor(Color.RED)
            R.id.context_green -> textView.setTextColor(Color.GREEN)
            R.id.context_blue -> textView.setTextColor(Color.BLUE)
        }
        return true
    }

    // Сontextual Action Mode
    private fun initСontextualActionMode() {
        binding.myListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL

        binding.myListView.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_lab7_actionmode, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.action_bold -> applyStyleToSelected(Typeface.BOLD)
                    R.id.action_italic -> applyStyleToSelected(Typeface.ITALIC)
                }
                mode?.finish()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {}
            override fun onItemCheckedStateChanged(
                mode: ActionMode?,
                position: Int,
                id: Long,
                checked: Boolean
            ) {}
        })
    }

    private fun applyStyleToSelected(style: Int) {
        val listView = binding.myListView
        for (i in 0 until listView.count) {
            if (listView.isItemChecked(i)) {
                val itemView = listView.getChildAt(i) as? TextView
                itemView?.setTypeface(null, style)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}