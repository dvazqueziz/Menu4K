package com.example.menu4k.fragments

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menu4k.R
import com.example.menu4k.adapters.AdaptadorDB
import com.example.menu4k.adapters.AdaptadorRecycleView
import com.example.menu4k.databinding.FragmentFavoritosBinding


class FavoritosFragment : Fragment() {
    private var _binding: FragmentFavoritosBinding? = null

    private val binding get()  = _binding!!
    private lateinit var criptoHelper: AdaptadorDB
    private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        criptoHelper= AdaptadorDB(view.context)
        db = criptoHelper.readableDatabase
        val cursor= criptoHelper.listarFavoritos()
        if (cursor.moveToFirst()){
            do {
                val adaptador = AdaptadorRecycleView()
                adaptador.AdaptadorRecycleView(cursor)
                binding.recyclerViewFavoritos.layoutManager= LinearLayoutManager(view.context)
                binding.recyclerViewFavoritos.adapter = adaptador
            }while (cursor.moveToNext())

        } else{

            Toast.makeText(view.context,"Añade primero Criptos a Favoritos",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }
}