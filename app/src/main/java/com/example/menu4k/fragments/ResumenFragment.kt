package com.example.menu4k.fragments

import android.database.Cursor
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.menu4k.adapters.AdaptadorDB

import com.example.menu4k.databinding.FragmentResumenBinding

class ResumenFragment : Fragment() {
    private var _binding: FragmentResumenBinding? = null

    private val binding get() = _binding!!
    private lateinit var criptoHelper: AdaptadorDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumenBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        criptoHelper = AdaptadorDB(view.context)

        val cursor= criptoHelper.resumen()
        if (cursor.moveToFirst()){
            do {
                print(cursor.getDouble(0))
                println(cursor.getString(1))

            }while (cursor.moveToNext())

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}