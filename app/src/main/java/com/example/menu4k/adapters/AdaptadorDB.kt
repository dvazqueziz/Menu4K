package com.example.menu4k.adapters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.menu4k.fragments.FavoritosFragment


class AdaptadorDB (context: Context): SQLiteOpenHelper(context,"datos.db",null,1) {

        val modeloBaseDatos = UtilityDB()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(modeloBaseDatos.crearTablaCripto)
        db.execSQL(modeloBaseDatos.crearTablaFavoritos)
        db.execSQL(modeloBaseDatos.crearTablaPortfolio)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun anadirDatos( nombre: String ,symboloCripto: String, precioActual: Double, imagenCripto: String, capitalizacionMercado: Long,
                     ranking : Int, maximoDiario: Double, minimoDiario: Double, cambioPrecioEuros: Double, cambioPrecioPorcien: Double, ath : Double, diferenciaAthPorcien:Double){
        val db :SQLiteDatabase =writableDatabase
        val datos = ContentValues()
        datos.put("nombreCriptomoneda", nombre)

        datos.put("symboloCriptomoneda",symboloCripto)
        datos.put("precioActualCriptomoneda",precioActual)
        datos.put("imagenCriptomoneda", imagenCripto)
        datos.put("capitalizaciondemercado", capitalizacionMercado)
        datos.put("rankin",ranking)
        datos.put("maximodiario",maximoDiario)
        datos.put("minimodiario", minimoDiario)
        datos.put("cambioprecio24hrseuros",cambioPrecioEuros)
        datos.put("cambioprecio24hrsporcentaje",cambioPrecioPorcien)
        datos.put("ath",ath)
        datos.put("diferenciaathporcien", diferenciaAthPorcien)

        db.insert("criptos",null,datos)
        db.close()

    }

    fun saberSiHayDatosEnBd():Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM criptos", null)
        return cursor.moveToFirst()



    }

    fun actualizarPrecio(id: Int, nombre: String,  symboloCripto: String, precioActual: Double, imagenCripto: String, capitalizacionMercado: Long,
                         ranking : Int, maximoDiario: Double, minimoDiario: Double, cambioPrecioEuros: Double, cambioPrecioPorcien: Double, ath : Double, diferenciaAthPorcien:Double){
        val args= arrayOf(id.toString())
        val db= this.writableDatabase
        val datos = ContentValues()
        datos.put("nombreCriptomoneda", nombre)

        datos.put("symboloCriptomoneda",symboloCripto)
        datos.put("precioActualCriptomoneda",precioActual)
        datos.put("imagenCriptomoneda",imagenCripto)
        datos.put("rankin",ranking)
        datos.put("capitalizaciondemercado",capitalizacionMercado)
        datos.put("maximodiario",maximoDiario)
        datos.put("minimodiario", minimoDiario)
        datos.put("cambioprecio24hrseuros",cambioPrecioEuros)
        datos.put("cambioprecio24hrsporcentaje",cambioPrecioPorcien)
        datos.put("ath",ath)
        datos.put("diferenciaathporcien", diferenciaAthPorcien)
        db.update("criptos",datos,"_id =?",args)
        db.close()

    }

    fun anadirAFavoritos(idCripto: Int){

        val db :SQLiteDatabase =writableDatabase
        val datos = ContentValues()
        datos.put("idCriptomoneda",idCripto)
        db.insert("favoritos",null,datos)

        db.close()


    }

    fun saberSiHayCriptoEnFavoritos(idCripto: Int): Boolean{
        val db = this.readableDatabase
        val cursor =db.rawQuery("SELECT * FROM favoritos WHERE idCriptomoneda= '$idCripto'",null)

        return cursor.moveToFirst()


    }
    //Funcion que elimina registro de la tabla Favoritos
    fun eliminarFavoritos(idCripto: Int){

        val db :SQLiteDatabase =writableDatabase

        db.delete("favoritos", "idCriptomoneda=?", arrayOf("$idCripto"))


    }

    fun anadirCompra(idCripto: Int, precioCompra: Double, cantidadToken: Double,fechaCompra:String){

        val db :SQLiteDatabase =writableDatabase
        val datos = ContentValues()
        datos.put("idCriptomoneda",idCripto)
        datos.put("precioCompra",precioCompra)
        datos.put("cantidadtoken",cantidadToken)
        datos.put("fechaCompra",fechaCompra)
        db.insert("portfolio",null,datos)


    }

    fun eliminarCompra(idCripto: Int){
        val db :SQLiteDatabase =writableDatabase

        db.delete("portfolio", "_id=?", arrayOf("$idCripto"))



    }

    fun sumaToken(idCripto: Int): Double{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT sum (cantidadtoken) FROM portfolio WHERE idCriptomoneda = '$idCripto'",null)
        cursor.moveToFirst()
        return cursor.getDouble(0)
    }
    fun sumaInversion(idCripto: Int):Double{

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT sum (precioCompra) FROM portfolio WHERE idCriptomoneda = '$idCripto'",null)
        cursor.moveToFirst()
        return cursor.getDouble(0)

    }

    fun resumen():Cursor{
        val db= this.readableDatabase

        val cursor = db.rawQuery(
            "SELECT criptos.precioActualCriptomoneda,symboloCriptomoneda,SUM(cantidadtoken) " +
                    "FROM criptos INNER JOIN portfolio ON portfolio.idCriptomoneda = criptos._id GROUP BY criptos.precioActualCriptomoneda,symboloCriptomoneda",
            null
        )
        return cursor
    }
    fun obtenerTodasLasCriptos():Cursor{
        val db= this.readableDatabase
     return db.rawQuery("SELECT * from criptos",null)
    }

    fun buscarCriptosEnLista(value: String):Cursor{
        val db= this.readableDatabase
        return db.rawQuery("SELECT * FROM criptos WHERE LIKE ('%$value%',nombreCriptomoneda)",null)

    }

    fun listarFavoritos(): Cursor{
        val db= this.readableDatabase

        return db.rawQuery("SELECT * FROM criptos "+
                "INNER JOIN favoritos"+
                "    ON criptos._id = favoritos.idCriptomoneda",null)
    }

    fun listarComprasPorCripto(idCripto: Int): Cursor{

        val db= this.readableDatabase
        return db.rawQuery(
            "SELECT idCriptomoneda,fechaCompra,cantidadtoken,precioCompra,symboloCriptomoneda,portfolio._id,precioActualCriptomoneda  FROM portfolio INNER JOIN criptos ON criptos._id =portfolio.idCriptomoneda WHERE idCriptomoneda=$idCripto",
            null
        )

    }
    fun rellenarRecyclerViewPortfolio(): Cursor{
        val db= this.readableDatabase
        return db.rawQuery("SELECT distinct idCriptomoneda,nombreCriptomoneda,imagenCriptomoneda,precioActualCriptomoneda FROM " +
                "criptos INNER JOIN portfolio on portfolio.idCriptomoneda = criptos._id",null)


    }


    fun rellenarCabeceraCompmraIndividualPortfolio(idCripto: Int): Cursor{

        val db= this.readableDatabase

        return db.rawQuery("SELECT nombreCriptomoneda,imagenCriptomoneda,precioActualCriptomoneda,symboloCriptomoneda FROM criptos WHERE _id=$idCripto",null)
    }

    fun rellenarDetalleCompraIndividual(idCripto: Int): Cursor{

        val db= this.readableDatabase
        return db.rawQuery("SELECT idCriptomoneda,fechaCompra,cantidadtoken,precioCompra,symboloCriptomoneda,portfolio._id,precioActualCriptomoneda FROM portfolio INNER JOIN criptos ON criptos._id =portfolio.idCriptomoneda WHERE idCriptomoneda=$idCripto",null)

    }


}