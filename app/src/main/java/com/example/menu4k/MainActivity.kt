package com.example.menu4k

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.menu4k.adapters.AdaptadorDB
import com.example.menu4k.databinding.ActivityMainBinding
import com.example.menu4k.fragments.BuscarFragment
import com.example.menu4k.fragments.FavoritosFragment
import com.example.menu4k.fragments.PortfolioFragment
import com.example.menu4k.fragments.ResumenFragment
lateinit var criptoAdaptador: AdaptadorDB
lateinit var db: SQLiteDatabase
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        extraerCriptos()
        val resumenFragment = ResumenFragment()
        val buscarFragment = BuscarFragment()
        val favoritosFragment = FavoritosFragment()
        val portfolioFragment = PortfolioFragment()
        cambiarFragment(resumenFragment)


        binding.menubottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btonMenuResumen -> cambiarFragment(resumenFragment)
                R.id.btnMenuBuscar -> cambiarFragment(buscarFragment)
                R.id.btnMenuFavoritos -> cambiarFragment(favoritosFragment)
                R.id.btonMenuPortfolio -> cambiarFragment(portfolioFragment)

            }
            true
        }



    }
    private fun cambiarFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutview, fragment)

            commit()


        }

    fun extraerCriptos() {

        criptoAdaptador = AdaptadorDB(this)
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&order=market_cap_desc&per_page=200&page=1&sparkline=false"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                if(!criptoAdaptador.saberSiHayDatosEnBd()){
                for (i in 0 until response.length()) {
                    val objetoJson = response.getJSONObject(i)

                    criptoAdaptador.anadirDatos(
                        objetoJson.getString("name"),
                        objetoJson.getString("symbol"),
                        objetoJson.getDouble("current_price"),
                        objetoJson.getString("image"),
                        objetoJson.getLong("market_cap"),
                        objetoJson.getInt("market_cap_rank"),
                        objetoJson.getDouble("high_24h"),
                        objetoJson.getDouble("low_24h"),
                        objetoJson.getDouble("price_change_24h"),
                        objetoJson.getDouble("price_change_percentage_24h"),
                        objetoJson.getDouble("ath"),
                        objetoJson.getDouble("ath_change_percentage")

                    )
                }

                }else{
                    for (i in 0 until response.length()){
                    val objetoJson = response.getJSONObject(i)
                    criptoAdaptador.actualizarPrecio(i+1,objetoJson.getString("name"),objetoJson.getString("symbol"),
                        objetoJson.getDouble("current_price"),objetoJson.getString("image"),objetoJson.getLong("market_cap"),objetoJson.getInt("market_cap_rank"),
                        objetoJson.getDouble("high_24h"),objetoJson.getDouble("low_24h"),objetoJson.getDouble("price_change_24h"),objetoJson.getDouble("price_change_percentage_24h"),objetoJson.getDouble("ath"),objetoJson.getDouble("ath_change_percentage"))
                }}

            },
            { error ->

                Toast.makeText(
                    this,
                    "Porfavor, comprueba tu conexion a internet....",
                    Toast.LENGTH_LONG
                ).show()
            }

        )

        queue.add(jsonArrayRequest)


    }
}
