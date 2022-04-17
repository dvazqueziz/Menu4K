package com.example.menu4k.adapters

import android.content.Context
import android.content.Intent
import android.database.Cursor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.menu4k.R
import com.example.menu4k.VistaDetalle
import com.example.menu4k.databinding.ItemPersonalizadoBinding
import com.squareup.picasso.Picasso



class AdaptadorRecycleView (): RecyclerView.Adapter<AdaptadorRecycleView.ViewHolder>(){
    lateinit var cursor: Cursor

    fun AdaptadorRecycleView(cursor: Cursor){

        this.cursor= cursor

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val criptoHelper= AdaptadorDB(view.context)
        val textoNombre : TextView
        val textoPrecio: TextView
        val imagenCripto: ImageView
        val simboloCripto: TextView
        val variacion : TextView
        val arribaAbajo: ImageView
        val corazonFavorito: ImageView

        init {
            val  binding = ItemPersonalizadoBinding.bind(view)
            textoNombre= binding.textNombreCripto
            textoPrecio= binding.textoPrecioCripto
            imagenCripto=binding.imagenCripto
            simboloCripto= binding.symboloCripto
            variacion=binding.precioVariacion
            arribaAbajo=binding.imageViewUpDown
            corazonFavorito=binding.btonFavoritoCorazon
            view.setOnClickListener{
              //  val posicion = adapterPosition
                cursor.moveToPosition(adapterPosition)
                val c:Context = view.context
                val intent = Intent(c, VistaDetalle::class.java)
                intent.putExtra("nombreCripto",cursor.getString(1))
                intent.putExtra("idCripto",cursor.getInt(0))
                intent.putExtra("symbolo",cursor.getString(2))
                intent.putExtra("precioActualCriptomoneda",cursor.getDouble(3))
                intent.putExtra("imagenCriptomoneda",cursor.getString(4))
                intent.putExtra("capitalizacionMercado",cursor.getLong(5))
                intent.putExtra("ranking",cursor.getInt(6))
                intent.putExtra("maximoDiario",cursor.getDouble(7))
                intent.putExtra("minimoDiario",cursor.getDouble(8))
                intent.putExtra("cambioPrecioEuros",cursor.getDouble(9))
                intent.putExtra("cambioPrecioPorciento",cursor.getDouble(10))
                intent.putExtra("ath",cursor.getDouble(11))
                intent.putExtra("diferenciaATH",cursor.getDouble(12))
                c.startActivity(intent)

            }
            corazonFavorito.setOnClickListener {

                cursor.moveToPosition(adapterPosition)
                if (!criptoHelper.saberSiHayCriptoEnFavoritos(cursor.getInt(0))){
                    criptoHelper.anadirAFavoritos(cursor.getInt(0))
                    corazonFavorito.setImageResource(R.drawable.ic_baseline_favorite_24_full_red)

                }else{


                    corazonFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    criptoHelper.eliminarFavoritos(cursor.getInt(0))


                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_personalizado,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val criptoHelper= AdaptadorDB(holder.itemView.context)

        cursor.moveToPosition(position)
        val idCripto= cursor.getInt(0)

        holder.textoNombre.text= cursor.getString(1)
        holder.textoPrecio.text= cursor.getDouble(3).toBigDecimal().toString()
        holder.simboloCripto.text= cursor.getString(2).uppercase()
        Picasso.get().load(cursor.getString(4)).into(holder.imagenCripto)

        holder.variacion.text= String.format("%.2f",cursor.getDouble(10))+"%"

        if(criptoHelper.saberSiHayCriptoEnFavoritos(idCripto)){
            holder.corazonFavorito.setImageResource(R.drawable.ic_baseline_favorite_24_full_red)

        }else{
            holder.corazonFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        }


        if (cursor.getDouble(10)>0){
            holder.textoPrecio.setTextColor(Color.GREEN)
            holder.arribaAbajo.setImageResource(R.drawable.ic_baseline_trending_up_24)

        }
        if(cursor.getDouble(10)<0){
            holder.textoPrecio.setTextColor(Color.RED)
            holder.arribaAbajo.setImageResource(R.drawable.ic_baseline_trending_down_24)
        }

    }

    override fun getItemCount(): Int {

        return cursor.count

    }

}