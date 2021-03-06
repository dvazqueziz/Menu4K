package com.example.menu4k.fragments

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menu4k.MainActivity
import com.example.menu4k.adapters.AdapadorPortfolio
import com.example.menu4k.adapters.AdaptadorDB

import com.example.menu4k.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {
    private var _binding: FragmentPortfolioBinding? = null

    private val binding get() = _binding!!
    private lateinit var criptoHelper: AdaptadorDB
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criptoHelper= AdaptadorDB(view.context)
        db = criptoHelper.readableDatabase

        val cursor: Cursor=db.rawQuery("SELECT distinct idCriptomoneda,nombreCriptomoneda,imagenCriptomoneda,precioActualCriptomoneda FROM " +
                "criptos INNER JOIN portfolio on portfolio.idCriptomoneda = criptos._id",null)

        rellenarRv(cursor)

    }

     fun rellenarRv(cursor: Cursor) {

        if (cursor.moveToFirst()){
            do {
                val adaptador = AdapadorPortfolio()
                adaptador.AdaptadorPortfolio(cursor)

                binding.recyclerViewPortfolio.layoutManager= LinearLayoutManager(view?.context)
                binding.recyclerViewPortfolio.adapter = adaptador
            }while (cursor.moveToNext())

        }
    }
}