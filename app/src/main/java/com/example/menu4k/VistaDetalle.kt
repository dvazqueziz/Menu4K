package com.example.menu4k

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import android.widget.Toast

import com.example.menu4k.adapters.AdaptadorDB
import com.example.menu4k.adapters.AdaptadorRecycleView
import com.example.menu4k.databinding.ActivityVistaDetalleBinding
import com.example.menu4k.fragments.DatePickerFragment
import com.example.menu4k.fragments.ResumenFragment
import com.squareup.picasso.Picasso

class VistaDetalle : AppCompatActivity() {
    lateinit var binding: ActivityVistaDetalleBinding
    val db: AdaptadorDB = AdaptadorDB(this)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaDetalleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent: Intent = intent
        binding.textNombreCripto.text = intent.getStringExtra("nombreCripto")
        binding.textPrecioActual.text =
            intent.getDoubleExtra("precioActualCriptomoneda", 0.00).toString() + "€"
        binding.textMarketCap.text =
            intent.getLongExtra("capitalizacionMercado", 0).toString() + "€"
        binding.textDiferenicaMaximo.text =
            intent.getDoubleExtra("diferenciaATH", 0.00).toString() + "%"
        binding.textMinimoDiario.text =
            intent.getDoubleExtra("minimoDiario", 0.00).toBigDecimal().toString() + "€"
        binding.textMaximoDiario.text =
            intent.getDoubleExtra("maximoDiario", 0.00).toBigDecimal().toString() + "€"
        binding.textRanking.text = intent.getIntExtra("ranking", 0).toString()
        binding.textMaximoHistorico.text =
            intent.getDoubleExtra("ath", 0.00).toBigDecimal().toString() + "€"
        binding.textVariacionPorcien.text =
            intent.getDoubleExtra("cambioPrecioPorciento", 0.00).toString() + "%"
        Picasso.get().load(intent.getStringExtra("imagenCriptomoneda"))
            .into(binding.imagenCriptoExt)
        val idCripto = intent.getIntExtra("idCripto", 0)

        if (db.saberSiHayCriptoEnFavoritos(idCripto)) {
            binding.btnBorrarFavoritos.isEnabled = true
            binding.btnFavoritos.isEnabled = false
        } else {
            binding.btnBorrarFavoritos.isEnabled = false
            binding.btnFavoritos.isEnabled = true
        }
        binding.btnFavoritos.setOnClickListener {
            db.anadirAFavoritos(idCripto)

            binding.btnFavoritos.isEnabled = false
            binding.btnBorrarFavoritos.isEnabled = true
        }
        binding.btnBorrarFavoritos.setOnClickListener {
            db.eliminarFavoritos(idCripto)
            binding.btnBorrarFavoritos.isEnabled = false
            binding.btnFavoritos.isEnabled = true

        }
        anadirCompraToken()

    }

    fun anadirCompraToken() {

        binding.btnAnadirCompra.setOnClickListener {
            val id = intent.getIntExtra("idCripto", 0)
            val invertido= binding.textDineroInvertido
            val token = binding.textCantidadToken
            val fechaCompra= binding.textFechaCompra

            if(isNull(invertido)||isNull(token)||isNull(fechaCompra)||menorACero(invertido)||menorACero(token)){
                alertaRojo()
            }else{
                db.anadirCompra(id, invertido.text.toString().toDouble(), token.text.toString().toDouble(), fechaCompra.text.toString())
                Toast.makeText(this, "Compra añadida", Toast.LENGTH_LONG).show()
                binding.textDineroInvertido.text = null
                binding.textCantidadToken.text = null
                binding.textFechaCompra.text = null
            }
        }
        binding.textFechaCompra.setOnClickListener { showDatePickerDialog() }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment({ year, month, day -> onDateSeleced(year, month, day) })
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSeleced(year: Int, month: Int, day: Int) {

        binding.textFechaCompra.text = "$year/$month/$day"

    }
    private fun isNull(value:TextView):Boolean{

        return (value.text.isEmpty())
    }
    private fun menorACero(value: TextView):Boolean{

        return value.text.toString().toDouble()<=0.0

    }

    private fun alertaRojo (){

         binding.textDineroInvertido.setHintTextColor(Color.RED)
        binding.textCantidadToken.setHintTextColor(Color.RED)
         binding.textFechaCompra.setHintTextColor(Color.RED)
        Toast.makeText(
            this,
            "Has introducido mal algun dato, compruebalo",
            Toast.LENGTH_LONG
        ).show()


    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}