package com.example.menu4k.adapters


import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menu4k.R
import com.example.menu4k.VistaPorfolioActivity

import com.example.menu4k.databinding.ItemCompraBinding
import com.google.android.material.card.MaterialCardView


class AdaptadorRecyclerCompras(var cursor: Cursor) :
    RecyclerView.Adapter<AdaptadorRecyclerCompras.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val fechaCompra: TextView
        val cantidadToken: TextView
        val inversion: TextView
        val valoracionUnitaria: TextView
        val gananciaUnitaria: TextView
        val cardView3: MaterialCardView

        init {
            val binding = ItemCompraBinding.bind(view)
            fechaCompra = binding.textFechaC
            cantidadToken = binding.textCantidadTokenC
            inversion = binding.textInversionC
            valoracionUnitaria = binding.textValoracionUnitariaCompra
            gananciaUnitaria = binding.gananciaUnitaria
            cardView3=binding.cardItemCompra

            binding.btnBorrar.setOnClickListener {
                var posicion = adapterPosition
                cursor.moveToPosition(posicion)
                AlertDialog.Builder(view.context)
                    .setTitle("Borrar compra")
                    .setMessage("Quiere eliminar la compra?")
                    .setPositiveButton("SI") { DialogInterface, _: Int ->
                        val idCripto = cursor.getInt(0)
                        val db = AdaptadorDB(view.context)
                        db.eliminarCompra(cursor.getInt(5))
                        cursor.moveToFirst()
                        //Ejecuto una nueva query sobre la bbdd para actualizar los datos en el RV
                        cursor = db.listarComprasPorCripto(idCripto)
                        notifyItemRemoved(posicion)

                    }
                    .setNegativeButton("NO") { DialogInterface, _: Int -> }
                    .show()

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_compra, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        cursor.moveToPosition(position)
        val roi = (((cursor.getDouble(2) * cursor.getDouble(6)) - cursor.getDouble(3)) / cursor.getDouble(3))
        holder.fechaCompra.append(cursor.getString(1))
        holder.inversion.append(" "+cursor.getDouble(3).toString() + " €")
        holder.cantidadToken.append(" "+cursor.getDouble(2).toBigDecimal().toString() + " ")
        holder.cantidadToken.append(cursor.getString(4))
        holder.valoracionUnitaria.append(" "+(cursor.getDouble(2) * cursor.getDouble(6)).toString()+" €")
        holder.gananciaUnitaria.append(String.format(" %.2f",roi))
        holder.gananciaUnitaria.append(" X")

        if(roi>0.00){

            holder.cardView3.strokeColor= Color.GREEN
        }else{

            holder.cardView3.strokeColor=Color.RED
        }

    }

    override fun getItemCount(): Int {

        return cursor.count

    }

}