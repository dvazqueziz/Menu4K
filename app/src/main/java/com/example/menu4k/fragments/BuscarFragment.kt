package com.example.menu4k.fragments

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menu4k.R
import com.example.menu4k.adapters.AdaptadorDB
import com.example.menu4k.adapters.AdaptadorRecycleView
import com.example.menu4k.criptoAdaptador
import com.example.menu4k.databinding.FragmentBuscarBinding

class BuscarFragment : Fragment() {

    private var _binding: FragmentBuscarBinding? = null

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
        _binding = FragmentBuscarBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criptoHelper= AdaptadorDB(view.context)
        db = criptoHelper.readableDatabase


        var cursor: Cursor = db.rawQuery("SELECT * FROM criptos",null)


        val adaptador = AdaptadorRecycleView()
        adaptador.AdaptadorRecycleView(cursor)

        binding.recyclerViewBuscar.layoutManager= LinearLayoutManager(view.context)
        binding.recyclerViewBuscar.adapter = adaptador

        binding.textSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
               //No la necesito implementar para este proyecto
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                cursor = db.rawQuery("SELECT * FROM criptos WHERE LIKE ('%$p0%',nombreCriptomoneda)",null)
                adaptador.AdaptadorRecycleView(cursor)
                binding.recyclerViewBuscar.layoutManager= LinearLayoutManager(view.context)
                binding.recyclerViewBuscar.adapter = adaptador

                return false
            }
        })

    }





}