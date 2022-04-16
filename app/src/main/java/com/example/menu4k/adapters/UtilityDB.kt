package com.example.menu4k.adapters

data class UtilityDB (

   private val nombreCripto :String="nombreCriptomoneda",
   private val idCriptomoneda:String ="idCriptomoneda",
   private val symboloCriptomoneda:String="symboloCriptomoneda",
   private val precioActual:String="precioActualCriptomoneda",
   private val precioCompra:String="precioCompra",
   private val imagenCriptomoneda:String="imagenCriptomoneda",
   private val capitalizacionMercado:String="capitalizaciondemercado",
   private val rankin:String="rankin",
   private val maximoDiario:String="maximodiario",
   private val minimoDiario:String="minimodiario",
   private val cambioUltimoDiaEuros:String="cambioprecio24hrseuros",
   private val cambioUltimoDiaPorcient:String="cambioprecio24hrsporcentaje",
   private val ath:String="ath",
   private val diferenciaathporcien:String="diferenciaathporcien",
   private val cantidadToken: String="cantidadtoken",
    private val fechaCompra: String="fechaCompra",
    //Variable que contiene el String para crear nuestra tabla Cripto
     val crearTablaCripto: String ="CREATE TABLE criptos" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$nombreCripto TEXT," +
            "$symboloCriptomoneda TEXT," +
            "$precioActual REAL," +
            "$imagenCriptomoneda TEXT," +
            "$capitalizacionMercado REAL," +
            "$rankin INTEGER," +
            "$maximoDiario REAL," +
            "$minimoDiario REAL," +
            "$cambioUltimoDiaEuros REAL," +
            "$cambioUltimoDiaPorcient REAL," +
            "$ath REAL," +
            "$diferenciaathporcien REAL)",
    //Variable que contiene el String para crear nuestra tabla Favoritos
    val crearTablaFavoritos: String="CREATE TABLE favoritos" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "$idCriptomoneda INTEGER," +
            "FOREIGN KEY($idCriptomoneda) REFERENCES criptos(_id))",

    //Variable que contiene el String para crear nuestra tabla Portfolio
    val crearTablaPortfolio : String="CREATE TABLE portfolio" +"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$idCriptomoneda INTEGER," +
            "$precioCompra REAL," +
            "$cantidadToken REAL," +
            "$fechaCompra TEXT," +
            "FOREIGN KEY($idCriptomoneda) REFERENCES criptos(_id))"



)