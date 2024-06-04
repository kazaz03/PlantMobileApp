package com.example.projekat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AbsListView.CHOICE_MODE_SINGLE
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NovaBiljkaActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    private var listaOkusaBiljke= getListaOkusa()
    private lateinit var listaOkusa: ListView

    private var listaMedicinskihKoristi = getMedicinskeKoristi()
    private lateinit var listaKoristi: ListView

    private var listaKlimatskihTipova = getKlimatskeTipove()
    private lateinit var listaKlima:ListView

    private var listaZemljisnihTipova = getZemljisneTipove()
    private lateinit var listaZemljista : ListView

    private var listaDodanihJela = mutableListOf<String>()
    private lateinit var jelaLV : ListView
    private lateinit var dodajJeloBtn : Button
    private lateinit var dodajBiljkuBtn : Button
    private lateinit var uslikajBiljkuBtn : Button

    //edittextovi
    private lateinit var unosNaziva : EditText
    private lateinit var unosPorodice : EditText
    private lateinit var unosMedicinskogUpozorenja : EditText
    private lateinit var jeloET : EditText

    private lateinit var adapterJela : ArrayAdapter<String>
    private lateinit var scrollView : ScrollView
    private lateinit var slikaIV : ImageView

    private lateinit var profilOkusaError: TextView
    private lateinit var medicinskaKoristError:TextView
    private lateinit var klimatskiTipError: TextView
    private lateinit var zemljisniTipError: TextView
    private lateinit var praznaListaJelaError: TextView
    private lateinit var duplikatJelaError: TextView
    private lateinit var dozvolaError: TextView
    private lateinit var latinskiNazivError: TextView
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_biljka)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        profilOkusaError=findViewById(R.id.profilOkusaError)
        medicinskaKoristError=findViewById(R.id.medicinskaKoristError)
        klimatskiTipError=findViewById(R.id.klimatskiTipError)
        zemljisniTipError=findViewById(R.id.zemljisniTipError)
        praznaListaJelaError=findViewById(R.id.praznaListaJelaError)
        duplikatJelaError=findViewById(R.id.duplikatJelaError)
        dozvolaError=findViewById(R.id.dozvolaError)
        latinskiNazivError=findViewById(R.id.latinskiNazivError)

        slikaIV=findViewById(R.id.slikaIV)
        scrollView=findViewById(R.id.main)
        jelaLV=findViewById(R.id.jelaLV)
        dodajJeloBtn=findViewById(R.id.dodajJeloBtn)
        dodajBiljkuBtn=findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuBtn=findViewById(R.id.uslikajBiljkuBtn)

        unosNaziva=findViewById(R.id.nazivET)
        unosPorodice=findViewById(R.id.porodicaET)
        unosMedicinskogUpozorenja=findViewById(R.id.medicinskoUpozorenjeET)
        jeloET=findViewById(R.id.jeloET)

        listaOkusa=findViewById(R.id.profilOkusaLV)
        val adapterOkusa=ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listaOkusaBiljke)
        listaOkusa.adapter=adapterOkusa
        listaOkusa.choiceMode=CHOICE_MODE_SINGLE

        val bojaTeksta=resources.getColor(R.color.bojateksta)

        //kako promjeniti boju simplelistitem
        listaOkusa.post {
            for (i in 0 until listaOkusa.childCount) {
                val textView = listaOkusa.getChildAt(i) as TextView
                textView.setTextColor(bojaTeksta)
            }
        }

        listaKoristi=findViewById(R.id.medicinskaKoristLV)
        val adapterKoristi=ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,listaMedicinskihKoristi)
        listaKoristi.adapter=adapterKoristi
        listaKoristi.choiceMode= CHOICE_MODE_MULTIPLE

        listaKoristi.post {
            for (i in 0 until listaKoristi.childCount) {
                val textView = listaKoristi.getChildAt(i) as TextView
                textView.setTextColor(bojaTeksta)
            }
        }

        listaKlima=findViewById(R.id.klimatskiTipLV)
        val adapterKlima=ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,listaKlimatskihTipova)
        listaKlima.adapter=adapterKlima
        listaKlima.choiceMode=CHOICE_MODE_MULTIPLE

        listaKlima.post {
            for (i in 0 until listaKlima.childCount) {
                val textView = listaKlima.getChildAt(i) as TextView
                textView.setTextColor(bojaTeksta)
            }
        }

        listaZemljista=findViewById(R.id.zemljisnjiTipLV)
        val adapterZemlja=ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,listaZemljisnihTipova)
        listaZemljista.adapter=adapterZemlja
        listaZemljista.choiceMode=CHOICE_MODE_MULTIPLE

        listaZemljista.post {
            for (i in 0 until listaZemljista.childCount) {
                val textView = listaZemljista.getChildAt(i) as TextView
                textView.setTextColor(bojaTeksta)
            }
        }

        adapterJela=ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaDodanihJela)
        jelaLV.adapter=adapterJela


        var odabranoJelo : String
        var indexOdabranog =-1

        jelaLV.setOnItemClickListener { parent, view, position, id ->
            odabranoJelo = parent.getItemAtPosition(position) as String
            indexOdabranog=position
            jeloET.setText(odabranoJelo)
            dodajJeloBtn.text = "Izmijeni jelo"
        }

        dodajJeloBtn.setOnClickListener{
            var opcija=dodajJeloBtn.text
            var tekst=jeloET.text
            if(!postojiJelo(tekst.toString())) {
                if (opcija.equals("Dodaj jelo")) {
                    //ako je na dodaj jelo
                    if (tekst.toString().length !in 2..20) {
                        jeloET.error = "Jelo mora imati između 2 i 20 znakova"
                    }else  {
                        praznaListaJelaError.visibility=View.GONE
                    listaDodanihJela.add(jeloET.text.toString())
                    adapterJela.notifyDataSetChanged()
                    jelaLV.post {
                        for (i in 0 until jelaLV.childCount) {
                            val textView = jelaLV.getChildAt(i) as TextView
                            textView.setTextColor(bojaTeksta)
                        }
                    }
                    }
                } else {
                    if (tekst.isEmpty()) {
                        listaDodanihJela.removeAt(indexOdabranog)
                        adapterJela.notifyDataSetChanged() //za brisanje
                        jelaLV.post {
                            for (i in 0 until jelaLV.childCount) {
                                val textView = jelaLV.getChildAt(i) as TextView
                                textView.setTextColor(bojaTeksta)
                            }
                        }
                        dodajJeloBtn.text = "Dodaj jelo"
                        jeloET.setText("")
                    } else {
                        if (tekst.toString().length !in 2..20) {
                            jeloET.error = "Jelo mora imati između 2 i 20 znakova"
                        }else
                        {
                        listaDodanihJela[indexOdabranog] = jeloET.text.toString().trim()
                        adapterJela.notifyDataSetChanged()
                        jelaLV.post {
                            for (i in 0 until jelaLV.childCount) {
                                val textView = jelaLV.getChildAt(i) as TextView
                                textView.setTextColor(bojaTeksta)
                            }
                        }
                        jeloET.setText("")
                        dodajJeloBtn.text = "Dodaj jelo"
                        }
                    }
                }
            }
        }

        dodajBiljkuBtn.setOnClickListener{
            if(validacija())
            {
                medicinskaKoristError.visibility= View.GONE
                profilOkusaError.visibility=View.GONE
                klimatskiTipError.visibility=View.GONE
                zemljisniTipError.visibility=View.GONE
                praznaListaJelaError.visibility=View.GONE
                duplikatJelaError.visibility=View.GONE
                latinskiNazivError.visibility=View.GONE
                val intent= Intent(this,MainActivity::class.java)
                //moze se implementirat kao serializable i onda poslaz samo biljka
                intent.putExtra("naziv",unosNaziva.text.toString())
                intent.putExtra("porodica",unosPorodice.text.toString())
                intent.putExtra("medicinskoUpozorenje",unosMedicinskogUpozorenja.text.toString())
                val indeksOkusa=listaOkusa.checkedItemPosition
                if (indeksOkusa != ListView.INVALID_POSITION) {
                    val adapter = listaOkusa.adapter
                    val profilOkusaTekst = adapter.getItem(indeksOkusa).toString()
                    intent.putExtra("profilOkusa",profilOkusaTekst)
                }
                //odabrane medicinske koristi
                val medicinskeKoristi =StringBuilder()
                val checkedPositions = listaKoristi.checkedItemPositions
                for (i in 0 until checkedPositions.size()) {
                    val position = checkedPositions.keyAt(i)
                    val isChecked = checkedPositions.valueAt(i)

                    if (isChecked) {
                        val adapter = listaKoristi.adapter
                        val selectedText = adapter.getItem(position).toString()
                        medicinskeKoristi.append("*$selectedText")
                    }
                }
                intent.putExtra("medicinskeKoristi",medicinskeKoristi.toString())
                //odabrani klimatski tipovi
                val klimatskiTipovi=StringBuilder()
                val checkedPositions2 = listaKlima.checkedItemPositions
                for (i in 0 until checkedPositions2.size()) {
                    val position = checkedPositions2.keyAt(i)
                    val isChecked = checkedPositions2.valueAt(i)

                    if (isChecked) {
                        val adapter = listaKlima.adapter
                        val selectedText = adapter.getItem(position).toString()
                        klimatskiTipovi.append(selectedText)
                    }
                }
                intent.putExtra("klimatskiTipovi",klimatskiTipovi.toString())

                //odabrani zemljisni tipovi
                val zemljisniTipovi=StringBuilder()
                val checkedPositions3= listaZemljista.checkedItemPositions
                for (i in 0 until checkedPositions3.size()) {
                    val position = checkedPositions3.keyAt(i)
                    val isChecked = checkedPositions3.valueAt(i)

                    if (isChecked) {
                        val adapter = listaZemljista.adapter
                        val selectedText = adapter.getItem(position).toString()
                        zemljisniTipovi.append(selectedText)
                    }
                }
                intent.putExtra("zemljisniTipovi",zemljisniTipovi.toString())

                //odabrana jela
                val odabranaJela=StringBuilder()
                for(element in listaDodanihJela)
                {
                    odabranaJela.append("$element*")
                }
                intent.putExtra("jelaBiljke",odabranaJela.toString())
                startActivity(intent)
            }else {

            }
        }

        uslikajBiljkuBtn.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            } else {
                dispatchTakePictureIntent()
            }
        }

    }

    private fun postojiJelo(jelo: String): Boolean {
        //provjerit u listi dodanih jela
        if(listaDodanihJela.any{it.lowercase()==jelo.lowercase()})
        {
            duplikatJelaError.visibility=View.VISIBLE
            duplikatJelaError.error="Jelo je već dodano u listi"
            return true
        }
        duplikatJelaError.visibility=View.GONE
        return false
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dozvolaError.visibility=View.GONE
                dispatchTakePictureIntent()
            } else {
                dozvolaError.error="Dozvola za kameru je odbijena"
                dozvolaError.visibility=View.VISIBLE
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaIV.setImageBitmap(imageBitmap)
        }
    }

    private fun validacija(): Boolean
    {
        val naziv=unosNaziva.text.toString()
        val porodica=unosPorodice.text.toString()
        val medicinskoUpozorenje=unosMedicinskogUpozorenja.text.toString()
        var validno=true

        if (naziv.length !in 2..40) {
            unosNaziva.error = "Naziv mora imati između 2 i 40 znakova"
            validno=false
        }

        //provjera ima li naziv u sebi latinski naziv
        if(!imaLatinskiNaziv(naziv))
        {
            latinskiNazivError.visibility=View.VISIBLE
            latinskiNazivError.error="Naziv mora imati i latinski naziv unesen"
            validno=false
        }else latinskiNazivError.visibility=View.GONE

        if (porodica.length !in 2..20) {
            unosPorodice.error="Porodica mora imati između 2 i 20 znakova"
            validno = false
        }
        if (medicinskoUpozorenje.length !in 2..20) {
            unosMedicinskogUpozorenja.error="Upozorenje mora imat između 2 i 20 znakova"
            validno = false
        }
        if(!nestaOdabrano(listaKoristi))
        {
            medicinskaKoristError.visibility=View.VISIBLE
            medicinskaKoristError.error="Mora biti odabrana bar jedna medicinska korist"
            validno=false
        }else  medicinskaKoristError.visibility= View.GONE
        if(!nestaOdabrano(listaKlima))
        {
            klimatskiTipError.visibility=View.VISIBLE
            klimatskiTipError.error="Mora biti odabrana bar jedna klima"
            validno=false
        }else klimatskiTipError.visibility=View.GONE
        if(!nestaOdabrano(listaZemljista))
        {
            zemljisniTipError.error="Mora biti odabrano bar jedno zemljiste"
            zemljisniTipError.visibility=View.VISIBLE
            validno=false
        }else zemljisniTipError.visibility=View.GONE
        if(listaDodanihJela.isEmpty())
        {
            praznaListaJelaError.error="Mora biti dodano barem jedno jelo u listi jela"
            praznaListaJelaError.visibility=View.VISIBLE
            validno=false
        }else praznaListaJelaError.visibility=View.GONE

        val odabrani=listaOkusa.checkedItemCount
        if(odabrani<1 || odabrani>1) {
            profilOkusaError.visibility=View.VISIBLE
            profilOkusaError.error="Mora biti odabran jedan okus"
            validno=false
        }else profilOkusaError.visibility=View.GONE
        return validno
    }

    private fun nestaOdabrano(listView: ListView):Boolean{
        for (i in 0 until listView.count) {
            if (listView.isItemChecked(i)) {
                return true
            }
        }
        return false
    }

    private fun imaLatinskiNaziv(tekst: String):Boolean{
        val regex = Regex("\\(.*?\\)")
        return regex.containsMatchIn(tekst)
    }

}