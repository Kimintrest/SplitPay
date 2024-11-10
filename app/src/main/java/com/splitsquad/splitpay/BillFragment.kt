package com.splitsquad.splitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView


class BillFragment : Fragment() {
    private var namesArr: Array<String> = arrayOf("Федя", "Игорь", "Катя", "Лиза", "Лёша")
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bill, container, false)
        val vie = inflater.inflate(R.layout.fragment_group, container, false)

        // Инициализация ListView
        listView = view.findViewById(R.id.listView)

        // Создание адаптера и установка его для ListView
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, namesArr)
        listView.adapter = adapter

        // Возвращаем представление
        return view
    }
}


