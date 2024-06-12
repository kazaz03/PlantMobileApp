package com.example.projekat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class TrefleDAO {

    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    private fun getDefaultBitmap(): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.defaultbiljka)
    }

    /*private var defaultBitmap: Bitmap=Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888).apply {
        eraseColor(android.graphics.Color.GRAY)
    }*/
    suspend fun getImage(plant: Biljka): Bitmap = withContext(Dispatchers.IO) {
        try {
            val latinskiNaziv=getLatinskiNaziv(plant.naziv)
            val response = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
            if (response.isSuccessful) {
                val plants = response.body()?.data
                if (!plants.isNullOrEmpty()) {
                    val imageUrl = plants[0].imageUrl
                    if (!imageUrl.isNullOrEmpty()) {
                        val inputStream = URL(imageUrl).openStream()
                        return@withContext BitmapFactory.decodeStream(inputStream)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@withContext getDefaultBitmap()//defaultBitmap
    }

    suspend fun fixData(plant: Biljka): Biljka= withContext(Dispatchers.IO){
        var porodica=plant.porodica
        var ime=plant.naziv
        var medicinskoUpozorenjeTekst=plant.medicinskoUpozorenje
        var medicinskeKoristi=plant.medicinskeKoristi
        var jelaBiljke=plant.jela.toMutableList()
        var listaZemljista= plant.zemljisniTipovi.toMutableList()
        var listaKlima= plant.klimatskiTipovi.toMutableList()
        var profilokusa=plant.profilOkusa
        try {
            val latinskiNaziv=getLatinskiNaziv(plant.naziv)
            val response=ApiAdapter.retrofit.searchPlants(latinskiNaziv)
            if(response.isSuccessful)
            {
                //ako je nasao treba da preuzme id od te biljke
                val plants=response.body()?.data
                if(!plants.isNullOrEmpty()){
                    val plantId=plants[0].id
                    val ispravljenlatinski=plants[0].scientificName
                    //sad pozivat ovu za id
                    if(plants[0].family!="") porodica=plants[0].family
                    //ispravljanje i naziva mozda i ne treba dodat
                    if(plants[0].commonName!="") ime=plants[0].commonName.plus(" ($ispravljenlatinski)")
                    val response2=ApiAdapter.retrofit.getPlantById(plantId)
                    if(response2.isSuccessful){
                        val plantMainSpecies= response2.body()?.data?.mainSpecies
                        val edibleOrNot= plantMainSpecies?.edible
                        if(edibleOrNot!=null && edibleOrNot.equals(false))
                        {
                            jelaBiljke.clear()
                            val staro=medicinskoUpozorenjeTekst
                            val novo=" NIJE JESTIVO"
                            medicinskoUpozorenjeTekst=staro+novo
                        }
                        val toxicityBiljke=plantMainSpecies?.specifications?.toxicity
                        if(toxicityBiljke!=null && toxicityBiljke!="none"){
                            if(!medicinskoUpozorenjeTekst.contains("TOKSIČNO")){
                                val staro=medicinskoUpozorenjeTekst
                                val novo=" TOKSIČNO"
                                medicinskoUpozorenjeTekst=staro+novo
                            }
                        }
                        val soilTextureOfPlant=plantMainSpecies?.growth?.soil_texture
                        if(soilTextureOfPlant!=null){
                            val odgovarajuceZemljiste=getZemljiste(soilTextureOfPlant)
                            //Log.d("zemljiste",odgovarajuceZemljiste.toString())
                            if(odgovarajuceZemljiste!=null) {
                                listaZemljista.clear();
                                listaZemljista.add(odgovarajuceZemljiste)
                            }
                        }
                        val lightOfPlant=plantMainSpecies?.growth?.light
                        val atmosphericHumidityOfPlant=plantMainSpecies?.growth?.atmosphericHumidity
                        if(lightOfPlant!=null && atmosphericHumidityOfPlant!=null){
                            val odgovarajuceKlime=getKlima(lightOfPlant,atmosphericHumidityOfPlant)
                            listaKlima.clear()
                            listaKlima=odgovarajuceKlime
                        }
                    }
                }
            }
        }catch(e: IOException){
            e.printStackTrace()
        }
        val novabiljka=Biljka(plant.id,ime,porodica,medicinskoUpozorenjeTekst,medicinskeKoristi,profilokusa,
            jelaBiljke.toList(),listaKlima.toList(),listaZemljista.toList(),true)
        //Log.d("novabiljka",novabiljka.toString())
        return@withContext novabiljka
    }

    suspend fun getPlantsWithFlowerColor(flowerColor:String,substr:String): List<Biljka> =withContext(Dispatchers.IO){
        var biljke= mutableListOf<Biljka>()
        try{
            val filters=mapOf("filter[flower_color]" to flowerColor)
            val first_response=ApiAdapter.retrofit.getPlantsByFlowerColor(substr,filters)
            if(first_response.isSuccessful){
                val plants=first_response.body()?.data
                if(!plants.isNullOrEmpty()){
                    //provjera koje biljke unutar liste imaju unutar scientific name podstring substr
                    for(plant in plants){
                        val ime=plant.commonName
                        val latinsko=plant.scientificName
                        val porodica=plant.family
                        //Log.d("biljka",plant.toString())
                        if((ime!=null && ime.lowercase().contains(substr.lowercase())) ||
                            (latinsko!=null && latinsko.lowercase().contains(substr.lowercase()))){ //il scientific
                            //ako sadrzi taj podstring sad vadimo ostale podatke za biljku
                            val second_response=ApiAdapter.retrofit.getPlantById(plant.id)
                            if(second_response.isSuccessful){
                                //var porodica=second_response.body()?.data?.mainSpecies?.family
                                val naziv=ime.plus(" ($latinsko)")
                                val listaZemljista=mutableListOf<Zemljiste>()
                                var listaKlima=mutableListOf<KlimatskiTip>()
                                var medUpozorenje=""
                                //vidjet jel jestivo
                                val jestivo=second_response.body()?.data?.mainSpecies?.edible
                                if(jestivo!=null && jestivo.equals(false))
                                {
                                    val staro=medUpozorenje
                                    val novo=" NIJE JESTIVO"
                                    medUpozorenje=staro+novo
                                }
                                val toksicno=second_response.body()?.data?.mainSpecies?.specifications?.toxicity
                                if(toksicno!=null && toksicno!="none")
                                {
                                    val staro=medUpozorenje
                                    val novo=" TOKSIČNO"
                                    medUpozorenje=staro+novo
                                }
                                //klimatski tip i zemljisni tip da se uzme
                                val soilTexture= second_response.body()?.data?.mainSpecies!!.growth.soil_texture
                                if(soilTexture!=null){
                                    val zemljiste=getZemljiste(soilTexture)
                                    if(zemljiste!=null) listaZemljista.add(zemljiste)
                                }
                                val light= second_response.body()?.data?.mainSpecies!!.growth.light
                                val atmosfera=second_response.body()?.data?.mainSpecies?.growth?.atmosphericHumidity
                                if(light!=null && atmosfera!=null){
                                    val klime=getKlima(light,atmosfera)
                                    listaKlima=klime
                                }
                                var biljka=Biljka(0,naziv,porodica,medUpozorenje, emptyList(),null,
                                    mutableListOf(),listaKlima,listaZemljista,false)
                                biljke.add(biljka)
                            }
                        }
                    }
                }
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
        return@withContext biljke
    }

    fun getKlima(light: Int, humidity: Int): MutableList<KlimatskiTip>{
        var listaKlima= mutableListOf<KlimatskiTip>()
        if(light in arrayOf(6,7,8,9) && humidity in arrayOf(1,2,3,4,5)) listaKlima.add(KlimatskiTip.SREDOZEMNA)
        if(light in arrayOf(8,9,10) && humidity in arrayOf(7,8,9,10)) listaKlima.add(KlimatskiTip.TROPSKA)
        if(light in arrayOf(6,7,8,9) && humidity in arrayOf(5,6,7,8)) listaKlima.add(KlimatskiTip.SUBTROPSKA)
        if(light in arrayOf(4,5,6,7) && humidity in arrayOf(3,4,5,6,7)) listaKlima.add(KlimatskiTip.UMJERENA)
        if (light in arrayOf(7,8,9) && humidity in arrayOf(1,2)) listaKlima.add(KlimatskiTip.SUHA)
        if(light in arrayOf(0,1,2,3,4,5) && humidity in arrayOf(3,4,5,6,7)) listaKlima.add(KlimatskiTip.PLANINSKA)
        return listaKlima
    }

    fun getLatinskiNaziv(puniNaziv: String): String{
        val pattern = "\\((.*?)\\)".toRegex()
        val matchResult = pattern.find(puniNaziv)
        return matchResult?.groupValues?.get(1)?:""
    }

    fun getZemljiste(broj: Int): Zemljiste?{
        if(broj==1 || broj==2) return Zemljiste.GLINENO
        else if(broj==3 || broj==4) return Zemljiste.PJESKOVITO
        else if(broj==5 || broj==6) return Zemljiste.ILOVACA
        else if(broj==7 || broj==8) return Zemljiste.CRNICA
        else if(broj==9) return Zemljiste.SLJUNKOVITO
        else if(broj==10) return Zemljiste.KRECNJACKO
        return null
        //vracat nista za zemljiste
    }
}