package com.example.menu4k.adapters

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.menu4k.R
import com.example.menu4k.VistaDetalle
import com.example.menu4k.VistaPorfolioActivity
import com.example.menu4k.databinding.ItemPersonalizadoBinding
import com.example.menu4k.databinding.ItemPortfolioBinding
import com.example.menu4k.db
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class AdapadorPortfolio: RecyclerView.Adapter<AdapadorPortfolio.ViewHolder>(){
    lateinit var cursor: Cursor

    fun AdaptadorPortfolio(cursor: Cursor){

        this.cursor= cursor

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textoNombre : TextView
        val tCantidadInvertida : TextView
        val imagenCripto: ImageView
        val textValoracionTotal: TextView


        init {
            val  binding = ItemPortfolioBinding.bind(view)
            textoNombre= binding.textNombreCripto
            tCantidadInvertida= binding.textCantidadInvertida
            imagenCripto=binding.imagenCripto
            textValoracionTotal=binding.textValoracionTotal

            view.setOnClickListener{
                var posicion = adapterPosition
                cursor.moveToPosition(posicion)
                val p= cursor.getInt(0)
                val c:Context = view.context

                val intent = Intent(c, VistaPorfolioActivity::class.java)
                intent.putExtra("idCripto",p)
                c.startActivity(intent)


            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_portfolio,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val criptoHelper = AdaptadorDB(holder.itemView.context)
        cursor.moveToPosition(position)

        val token: Double = criptoHelper.sumaToken(cursor.getInt(0))
        val precioActual : Double = cursor.getDouble(3)
        holder.textoNombre.text= cursor.getString(1)
        holder.tCantidadInvertida.text=criptoHelper.sumaInversion(cursor.getInt(0)).toString()
        holder.textValoracionTotal.text=String.format("%.2f",(token*precioActual))+"€"
        Picasso.get().load(cursor.getString(2)).into(holder.imagenCripto)

    }

    override fun getItemCount(): Int {

        return cursor.count

    }

}