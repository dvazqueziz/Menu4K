package com.example.menu4k

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menu4k.adapters.AdaptadorDB
import com.example.menu4k.adapters.AdaptadorRecycleView
import com.example.menu4k.adapters.AdaptadorRecyclerCompras

import com.example.menu4k.databinding.ActivityVistaPorfolioBinding
import com.squareup.picasso.Picasso

class VistaPorfolioActivity : AppCompatActivity() {
    lateinit var binding: ActivityVistaPorfolioBinding
    val criptoHelper= AdaptadorDB(this)
    lateinit var cursor: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaPorfolioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent: Intent = intent
        val idCripto= intent.getIntExtra("idCripto",0)

        db = criptoHelper.readableDatabase
        rellenarCabezeraPortfolio(idCripto)
        rellenarDetallePortfolio(idCripto)


    }
    //Función que rellena la cabecera en el detalle del portolio. Al acceder al detalle de compras
    fun rellenarCabezeraPortfolio(idCripto:Int) {


        cursor= db.rawQuery("SELECT nombreCriptomoneda,imagenCriptomoneda,precioActualCriptomoneda,symboloCriptomoneda FROM criptos WHERE _id=$idCripto",null)
        cursor.moveToFirst()
        val symbolo= cursor.getString(3)
        binding.textCanitdadTokenPortfolioExt.append(criptoHelper.sumaToken(idCripto).toBigDecimal().toString())
        binding.textCanitdadTokenPortfolioExt.append(" $symbolo")
        binding.textCantidadInvertidaPorftolioExt.append(criptoHelper.sumaInversion(idCripto).toString())
        binding.textCantidadInvertidaPorftolioExt.append(" €")
        binding.textValoracionPortfolioExt.append(String.format("%.2f",(criptoHelper.sumaToken(idCripto)*cursor.getDouble(2))))
        binding.textValoracionPortfolioExt.append(" €")
        binding.textNombreCriptoPortfolioExt.text=cursor.getString(0)
        Picasso.get().load(cursor.getString(1)).into(binding.imageView5)
    }
    //Con esta función relleno el detalle de las compras la primera vez que se crea la Vista
    private fun rellenarDetallePortfolio(idCripto: Int) {

        cursor= db.rawQuery("SELECT idCriptomoneda,fechaCompra,cantidadtoken,precioCompra,symboloCriptomoneda,portfolio._id FROM portfolio INNER JOIN criptos ON criptos._id =portfolio.idCriptomoneda WHERE idCriptomoneda=$idCripto",null)

        cursor.moveToFirst()

        if (cursor.moveToFirst()){

            do {
                val adaptador = AdaptadorRecyclerCompras(cursor)

                binding.rvVistaPortfolio.layoutManager= LinearLayoutManager(this)
                binding.rvVistaPortfolio.adapter= adaptador


            }while (cursor.moveToNext())

        }

    }



}
