package com.example.projekat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class TrefleDAO {

        private var defaultBitmap: Bitmap=Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888).apply {
            eraseColor(android.graphics.Color.WHITE)
        }
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
            return@withContext defaultBitmap
            }

        suspend fun fixData(plant: Biljka): Biljka= withContext(Dispatchers.IO){
            try {
                val latinskiNaziv=getLatinskiNaziv(plant.naziv)
                val response=ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if(response.isSuccessful)
                {
                    //ako je nasao treba da preuzme id od te biljke
                    val plants=response.body()?.data
                    if(!plants.isNullOrEmpty()){
                        val plantId=plants[0].id
                        //sad pozivat ovu za id
                        if(plants[0].family!="") plant.porodica=plants[0].family
                        //ispravljanje i naziva mozda i ne treba dodat
                        //if(plants[0].commonName!="") plant.naziv=plants[0].commonName.plus(" ($latinskiNaziv)")
                        val response2=ApiAdapter.retrofit.getPlantById(plantId)
                        if(response2.isSuccessful){
                            val plantMainSpecies= response2.body()?.data?.mainSpecies
                            val edibleOrNot= plantMainSpecies?.edible
                            if(edibleOrNot!=null && edibleOrNot.equals(false))
                            {
                                plant.jela.clear()
                                val staroMedicinskoUpozorenje=plant.medicinskoUpozorenje
                                val novo=staroMedicinskoUpozorenje.plus(" NIJE JESTIVO")
                                plant.medicinskoUpozorenje=novo
                            }
                            val toxicityBiljke=plantMainSpecies?.specifications?.toxicity
                            if(toxicityBiljke!=null){
                                if(!plant.medicinskoUpozorenje.contains("TOKSIČNO")){
                                    val staro=plant.medicinskoUpozorenje
                                    val novo=staro.plus("TOKSIČNO")
                                    plant.medicinskoUpozorenje=novo
                                }
                            }
                            val soilTextureOfPlant=plantMainSpecies?.growth?.soil_texture
                            if(soilTextureOfPlant!=null){
                            val odgovarajuceZemljiste=getZemljiste(soilTextureOfPlant)
                                var brojac=0
                            for(zemljiste in plant.zemljisniTipovi){
                                if (odgovarajuceZemljiste != null) {
                                    if(!zemljiste.naziv.equals(odgovarajuceZemljiste.naziv)) {
                                        plant.zemljisniTipovi.remove(zemljiste)
                                        if(brojac==0) plant.zemljisniTipovi.add(odgovarajuceZemljiste)
                                        brojac++
                                    }
                                }
                            }
                            }

                            val lightOfPlant=plantMainSpecies?.growth?.light
                            val atmosphericHumidityOfPlant=plantMainSpecies?.growth?.atmosphericHumidity
                            if(lightOfPlant!=null && atmosphericHumidityOfPlant!=null){
                                val odgovarajucaKlima=getKlima(lightOfPlant,atmosphericHumidityOfPlant)
                                var brojac=0
                                for(klima in plant.klimatskiTipovi) {
                                    if (odgovarajucaKlima != null) {
                                        if(!klima.opis.equals(odgovarajucaKlima.opis)){
                                            plant.klimatskiTipovi.remove(klima)
                                            if(brojac==0) plant.klimatskiTipovi.add(odgovarajucaKlima)
                                            brojac++
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }catch(e: IOException){
                e.printStackTrace()
            }
            return@withContext plant
        }

        suspend fun getPlantsWithFlowerColor(flower_color:String,substr:String): List<Biljka> =withContext(Dispatchers.IO){
            var biljke= mutableListOf<Biljka>()
            var listaIdova= mutableListOf<Int>()
            try{
                //pretraga po dijelu
                val response=ApiAdapter.retrofit.searchPlants(substr)
                if(response.isSuccessful){
                    val plants=response.body()?.data //lista biljaka od kojih sad trebam uzet id
                    if(!plants.isNullOrEmpty()){
                        for(plant in plants){
                            listaIdova.add(plant.id)
                        }
                    }
                    //sad pozivamo da nadjemo preko id ostale atribute
                    if(listaIdova.isNotEmpty())
                    for(id in listaIdova)
                    {
                        Log.d("idovi biljaka",id.toString())
                        val response2=ApiAdapter.retrofit.getPlantById(id)
                        if(response2.isSuccessful){
                            //ako uspije vratit biljku
                            val bojeBiljke=response2.body()?.data?.mainSpecies?.flower?.color
                            if(bojeBiljke!=null && bojeBiljke.contains(flower_color)){
                                /*gledat da li ima i substring u necemu nez jel u nazivu il u latinskom nazivu*/
                                //sad treba napravit biljku
                                val nazivnepotpun=response2.body()?.data?.commonName
                                var porodica=response2.body()?.data?.mainSpecies?.family
                                val latinskiNaziv=response2.body()?.data?.scientificName
                                val naziv=nazivnepotpun.plus(" ($latinskiNaziv)")
                                val listaZemljista=mutableListOf<Zemljiste>()
                                val listaKlima=mutableListOf<KlimatskiTip>()
                                val medUpozorenje=""
                                //vidjet jel jestivo
                                val jestivo=response2.body()?.data?.mainSpecies?.edible
                                if(jestivo!=null && jestivo.equals(false))
                                {
                                    medUpozorenje.plus("NIJE JESTIVO")
                                }
                                val toksicno=response2.body()?.data?.mainSpecies?.specifications?.toxicity
                                if(toksicno!=null)
                                {
                                    medUpozorenje.plus(" TOKSIČNO")
                                }
                                if(porodica==null) porodica=""
                                //klimatski tip i zemljisni tip da se uzme
                                val soilTexture= response2.body()?.data?.mainSpecies!!.growth.soil_texture
                                if(soilTexture!=null){
                                    val zemljiste=getZemljiste(soilTexture)
                                    if(zemljiste!=null) listaZemljista.add(zemljiste)
                                }
                                val light= response2.body()?.data?.mainSpecies!!.growth.light
                                val atmosfera=response2.body()?.data?.mainSpecies?.growth?.atmosphericHumidity
                                if(light!=null && atmosfera!=null){
                                    val klima=getKlima(light,atmosfera)
                                    if(klima!=null) listaKlima.add(klima)
                                }
                                var biljka=Biljka(naziv,porodica,medUpozorenje, emptyList(),null,
                                    mutableListOf(),listaKlima,listaZemljista)

                                biljke.add(biljka)
                            }
                        }
                    }
                }
            }catch(e: IOException){
                e.printStackTrace()
            }
            return@withContext biljke
        }

    fun getKlima(light: Int, humidity: Int): KlimatskiTip?{
        if(light in arrayOf(6,7,8,9) && humidity in arrayOf(1,2,3,4,5)) return KlimatskiTip.SREDOZEMNA
        else if(light in arrayOf(8,9,10) && humidity in arrayOf(7,8,9,10)) return KlimatskiTip.TROPSKA
        else if(light in arrayOf(6,7,8,9) && humidity in arrayOf(5,6,7,8)) return KlimatskiTip.SUBTROPSKA
        else if(light in arrayOf(4,5,6,7) && humidity in arrayOf(3,4,5,6,7)) return KlimatskiTip.UMJERENA
        else if (light in arrayOf(7,8,9) && humidity in arrayOf(1,2)) return KlimatskiTip.SUHA
        else if(light in arrayOf(0,1,2,3,4,5) && humidity in arrayOf(3,4,5,6,7)) return KlimatskiTip.PLANINSKA
        return null
        //ovdje ispravit ako je 0 do 5 da bude planinski i da bude null
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