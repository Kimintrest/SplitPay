package com.splitsquad.splitpay
// Замените на ваш пакет

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BillFragment : Fragment() {

    private var namesArr: Array<String> = arrayOf("Федя", "Игорь", "Катя", "Лиза", "Лёша", "Иван", "Антон", "Андрей",)
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфлейтим разметку фрагмента
        val view = inflater.inflate(R.layout.fragment_bill, container, false)

        // Находим ListView в разметке
        listView = view.findViewById(R.id.listView)

        // Создаем адаптер и устанавливаем его для ListView
        val adapter = CustomAdapter(requireActivity(), namesArr)
        listView.adapter = adapter

        return view
    }

    // Внутренний класс адаптера для ListView
    private class CustomAdapter(context: Context, private val names: Array<String>) :
        ArrayAdapter<String>(context, R.layout.name_item, names) {

        @SuppressLint("MissingInflatedId")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Инфлейтим элемент списка
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.name_item, parent, false)

            // Находим элементы внутри элемента списка
            val userNameTextView = view.findViewById<TextView>(R.id.user_name)
            val actionButton = view.findViewById<Button>(R.id.deb)

            // Устанавливаем текст для TextView
            userNameTextView.text = names[position]

            // Устанавливаем обработчик клика для кнопки
            actionButton.setOnClickListener {
                Toast.makeText(context, "Clicked on ${names[position]}", Toast.LENGTH_SHORT).show()
            }

            return view
        }
    }
}
