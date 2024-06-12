package com.example.projekat

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var filtriranaLista: MutableList<Biljka> = mutableListOf()
    private lateinit var biljkeView: RecyclerView
    private lateinit var biljkeAdapter1: BiljkaAdapterMedicinska
    private lateinit var biljkeAdapter2: BiljkaAdapterBotanicka
    private lateinit var biljkeAdapter3: BiljkaAdapterKuharska
    private lateinit var biljke: List<Biljka>
    private lateinit var botanickeFuncionalnosti: LinearLayout
    private lateinit var pretragaET: EditText
    private lateinit var brzaPretraga: Button
    private lateinit var bojaSPIN: Spinner
    private var trefleDAO=TrefleDAO()
    private var listaBoja=getBoje()
    private var rezultatPretrage=mutableListOf<Biljka>()
    private var odabranaBoja=""
    private lateinit var spinnerRV:Spinner
    private lateinit var context:Context
    private lateinit var networkChangeReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        context=this
        trefleDAO.setContext(context)

        val db=BiljkaDatabase.getInstance(context)

        botanickeFuncionalnosti=findViewById(R.id.botanickeFunkcionalnosti)
        spinnerRV=findViewById(R.id.modSpinner)
        biljkeView=findViewById(R.id.biljkeRV)
        biljkeView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        pretragaET=findViewById(R.id.pretragaET)
        brzaPretraga=findViewById(R.id.brzaPretraga)
        bojaSPIN=findViewById(R.id.bojaSPIN)

        val bojeAdapter=ArrayAdapter(this, android.R.layout.simple_spinner_item,listaBoja)
        bojeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bojaSPIN.adapter=bojeAdapter

        //dodavanje opcija u spinner
        val opcije = listOf("Medicinski", "Kuharski", "Botanički")
        //adapter za popunjavanje
        val spinner_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcije)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerRV.adapter=spinner_adapter

        biljkeAdapter1=BiljkaAdapterMedicinska(context,mutableListOf())
        biljkeAdapter2= BiljkaAdapterBotanicka(context,mutableListOf())
        biljkeAdapter3=BiljkaAdapterKuharska(context,mutableListOf())
        botanickeFuncionalnosti.visibility=View.GONE

        //uzimanje biljaka iz baze
        val scope=CoroutineScope(Job() + Dispatchers.Main)

        networkChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    scope.launch {
                        var a=0
                        db.biljkaDAO().fixOfflineBiljka()
                        Log.d("broj ispravljenih",a.toString())
                        biljke=db.biljkaDAO().getAllBiljkas()
                    }
                }
            }
        }
        // registracija BroadcastReceivera
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)

        scope.launch{
            /*db.biljkaDAO().clearData()
            for(plant in getBiljke())
                db.biljkaDAO().saveBiljka(plant)*/
            biljke = db.biljkaDAO().getAllBiljkas()
            biljkeView.setPadding(0,0,0,spinnerRV.height)
            biljkeView.adapter=biljkeAdapter1
            biljkeAdapter1.updateBiljke(biljke)
            spinnerRV.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedOption: String = parent?.getItemAtPosition(position).toString()
                    if(selectedOption=="Medicinski")
                    {
                        biljkeView.setPadding(0,0,0,spinnerRV.height)
                        botanickeFuncionalnosti.visibility=View.GONE
                        pretragaET.setText("")
                        biljkeAdapter2.postaviNaFalse()
                        biljkeView.adapter=biljkeAdapter1
                        if (filtriranaLista.isNotEmpty()) {
                            biljkeAdapter1.updateBiljke(filtriranaLista)
                        }else
                            biljkeAdapter1.updateBiljke(biljke)
                    }else if(selectedOption=="Botanički")
                    {
                        biljkeView.setPadding(0,0,0,165+165) //k
                        botanickeFuncionalnosti.visibility=View.VISIBLE //kad je u botanickim da se vidi
                        biljkeView.adapter=biljkeAdapter2
                        if (filtriranaLista.isNotEmpty()) {
                            biljkeAdapter2.updateBiljke(filtriranaLista)
                        }else
                            biljkeAdapter2.updateBiljke(biljke)
                    }else
                    {
                        biljkeView.setPadding(0,0,0,spinnerRV.height)
                        botanickeFuncionalnosti.visibility=View.GONE
                        pretragaET.setText("")
                        biljkeAdapter2.postaviNaFalse()
                        biljkeView.adapter=biljkeAdapter3
                        if (filtriranaLista.isNotEmpty()) {
                            biljkeAdapter3.updateBiljke(filtriranaLista)
                        }else
                            biljkeAdapter3.updateBiljke(biljke)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    biljkeAdapter1=BiljkaAdapterMedicinska(context,listOf())
                    biljkeView.adapter=biljkeAdapter1
                    biljkeAdapter1.updateBiljke(biljke)
                }
            }
        }

        if(!NetworkUtils.isNetworkAvailable(this)){
            Toast.makeText(this,"Slike biljaka se dobavljaju iz baze", Toast.LENGTH_LONG).show()
        }

        bojaSPIN.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                val selectedOption: String = parent?.getItemAtPosition(position).toString()
                odabranaBoja=selectedOption
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                odabranaBoja=""
            }
        }

        //ovdje dodajemo listener za svaki adapter posebno kad se klikne u modu
        biljkeAdapter1.setOnItemClickListener(object : BiljkaAdapterMedicinska.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                filtriranaLista= biljkeAdapter1.filterByCriteria(biljka).toMutableList()
            }
        })

        biljkeAdapter2.setOnItemClickListener(object : BiljkaAdapterBotanicka.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                if(!biljkeAdapter2.jeLiPretragaObavljena())
                    filtriranaLista= biljkeAdapter2.filterByCriteria(biljka).toMutableList()
            }
        })

        biljkeAdapter3.setOnItemClickListener(object : BiljkaAdapterKuharska.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                filtriranaLista= biljkeAdapter3.filterByCriteria(biljka).toMutableList()
            }
        })
        var resetButton:Button;
        resetButton=findViewById(R.id.resetBtn)
        resetButton.setOnClickListener {
            biljkeAdapter1.updateBiljke(biljke)
            biljkeAdapter2.updateBiljke(biljke)
            biljkeAdapter3.updateBiljke(biljke)
            pretragaET.setText("")
            biljkeAdapter2.postaviNaFalse()
            filtriranaLista= mutableListOf()
        }

        //dodavanje nove
        var dodajButton=findViewById<Button>(R.id.novaBiljkaBtn)
        dodajButton.setOnClickListener{
            var otvori_dodajIntent= Intent(this,NovaBiljkaActivity::class.java);
            startActivity(otvori_dodajIntent)
        }

        //brza pretraga button kad se pritisne
        brzaPretraga.setOnClickListener {
            val scope=CoroutineScope(Job() + Dispatchers.Main)
            if(pretragaET.text.toString()!="") {
                scope.launch {
                    rezultatPretrage = trefleDAO.getPlantsWithFlowerColor(
                        odabranaBoja,
                        pretragaET.text.toString().trim()
                    ).toMutableList()
                    biljkeAdapter2.postaviNaTrue()
                    biljkeAdapter2.updateBiljke(rezultatPretrage)
                }
            }
        }

        val extras=intent.extras
        if (extras != null) {
            //onda treba napravit biljku i dodat je
            val naziv2=extras.getString("naziv")
            val porodica2=extras.getString("porodica")
            val medicinskoUpozorenje2=extras.getString("medicinskoUpozorenje")
            val profilOkusa=extras.getString("profilOkusa")
            val medicinskeKoristiTekst=extras.getString("medicinskeKoristi")
            val klimatskiTipoviTekst=extras.getString("klimatskiTipovi")
            val zemljisniTipoviTekst=extras.getString("zemljisniTipovi")
            val jelaTekst=extras.getString("jelaBiljke")

            //pravljenje listi
            val Okus2 : ProfilOkusaBiljke
            if(profilOkusa?.contains("Gorak") == true)
            {
                Okus2=ProfilOkusaBiljke.GORKO
            }else if(profilOkusa?.contains("Sladak") == true)
            {
                Okus2=ProfilOkusaBiljke.SLATKI
            }else if(profilOkusa?.contains("Citrusni") == true)
            {
                Okus2=ProfilOkusaBiljke.CITRUSNI
            }else if(profilOkusa?.contains("Mentol") == true)
            {
                Okus2=ProfilOkusaBiljke.MENTA
            }else if(profilOkusa?.contains("Obični biljni okus") == true)
            {
                Okus2=ProfilOkusaBiljke.BEZUKUSNO
            }else if(profilOkusa?.contains("Ljuto") == true)
            {
                Okus2=ProfilOkusaBiljke.LJUTO
            }else if(profilOkusa?.contains("Začinski") == true)
            {
                Okus2=ProfilOkusaBiljke.AROMATICNO
            }else {
                Okus2=ProfilOkusaBiljke.KORIJENASTO
            }

            val medicinskeKoristiLista = mutableListOf<MedicinskaKorist>()
            if (medicinskeKoristiTekst != null) {
                if(medicinskeKoristiTekst.contains("Smirenje")) medicinskeKoristiLista.add(MedicinskaKorist.SMIRENJE)
                if(medicinskeKoristiTekst.contains("Protuupalno")) medicinskeKoristiLista.add(MedicinskaKorist.PROTUUPALNO)
                if(medicinskeKoristiTekst.contains("Protivbolova")) medicinskeKoristiLista.add(MedicinskaKorist.PROTIVBOLOVA)
                if(medicinskeKoristiTekst.contains("imunitetu")) medicinskeKoristiLista.add(MedicinskaKorist.PODRSKAIMUNITETU)
                if(medicinskeKoristiTekst.contains("probave")) medicinskeKoristiLista.add(MedicinskaKorist.REGULACIJAPROBAVE)
                if(medicinskeKoristiTekst.contains("pritiska")) medicinskeKoristiLista.add(MedicinskaKorist.REGULACIJAPRITISKA)
            }

            val klimatskiTipoviLista = mutableListOf<KlimatskiTip>()
            if(klimatskiTipoviTekst!=null)
            {
                if(klimatskiTipoviTekst.contains("Umjerena klima")) klimatskiTipoviLista.add(KlimatskiTip.UMJERENA)
                if(klimatskiTipoviTekst.contains("Sušna klima")) klimatskiTipoviLista.add(KlimatskiTip.SUHA)
                if(klimatskiTipoviTekst.contains("Tropska")) klimatskiTipoviLista.add(KlimatskiTip.TROPSKA)
                if(klimatskiTipoviTekst.contains("Mediteranska")) klimatskiTipoviLista.add(KlimatskiTip.SREDOZEMNA)
                if(klimatskiTipoviTekst.contains("Subtropska")) klimatskiTipoviLista.add(KlimatskiTip.SUBTROPSKA)
                if(klimatskiTipoviTekst.contains("Planinska")) klimatskiTipoviLista.add(KlimatskiTip.PLANINSKA)
            }

            val zemljisniTipoviLista = mutableListOf<Zemljiste>()
            if(zemljisniTipoviTekst!=null)
            {
                if(zemljisniTipoviTekst.contains("Krečnjačko")) zemljisniTipoviLista.add(Zemljiste.KRECNJACKO)
                if(zemljisniTipoviTekst.contains("Crnica")) zemljisniTipoviLista.add(Zemljiste.CRNICA)
                if(zemljisniTipoviTekst.contains("Glineno")) zemljisniTipoviLista.add(Zemljiste.GLINENO)
                if(zemljisniTipoviTekst.contains("Ilovača")) zemljisniTipoviLista.add(Zemljiste.ILOVACA)
                if(zemljisniTipoviTekst.contains("Pjeskovito")) zemljisniTipoviLista.add(Zemljiste.PJESKOVITO)
                if(zemljisniTipoviTekst.contains("Šljunovito")) zemljisniTipoviLista.add(Zemljiste.SLJUNKOVITO)
            }

            val listaJela = mutableListOf<String>()
            var trenutniString = ""
            if (jelaTekst != null) {
                for (char in jelaTekst) {
                    if (char != '*') {
                        trenutniString += char
                    } else {
                        listaJela.add(trenutniString.trim())
                        trenutniString = ""
                    }
                }
            }

            var novabiljka=Biljka(
                naziv =naziv2.toString(), porodica =porodica2.toString(),
                medicinskoUpozorenje =medicinskoUpozorenje2.toString(), medicinskeKoristi =medicinskeKoristiLista.toList(),
                profilOkusa =Okus2, jela = listaJela.toMutableList(), klimatskiTipovi = klimatskiTipoviLista.toMutableList(),
                zemljisniTipovi = zemljisniTipoviLista,onlineChecked=false)

            var novaPopravljenaBiljka=Biljka(0,"", "","", emptyList(),
               null, emptyList(), emptyList(), emptyList(),false)
            val scope= CoroutineScope(Job() + Dispatchers.Main)
            scope.launch{
                if(NetworkUtils.isNetworkAvailable(context)){
                    novaPopravljenaBiljka=trefleDAO.fixData(novabiljka)
                    Log.d("nova2",novaPopravljenaBiljka.toString())
                    if(naziv2!=null){
                        db.biljkaDAO().saveBiljka(novaPopravljenaBiljka)
                    }
                }else {
                    if(naziv2!=null)
                        db.biljkaDAO().saveBiljka(novabiljka)
                }
                biljke= db.biljkaDAO().getAllBiljkas()
                biljkeAdapter1.updateBiljke(biljke)
                biljkeAdapter2.updateBiljke(biljke)
                biljkeAdapter3.updateBiljke(biljke)
                biljkeView.adapter=biljkeAdapter1
            }
        };
    }
    override fun onDestroy() {
        super.onDestroy()
        // ponistavanje BroadcastReceivera kada se aktivnost uništi
        unregisterReceiver(networkChangeReceiver)
    }
}