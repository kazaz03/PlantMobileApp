package com.example.projekat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var filtriranaLista: List<Biljka> = emptyList()
    private lateinit var biljkeView: RecyclerView
    private lateinit var biljkeAdapter1: BiljkaAdapterMedicinska
    private lateinit var biljkeAdapter2: BiljkaAdapterBotanicka
    private lateinit var biljkeAdapter3: BiljkaAdapterKuharska
    private var biljke = getBiljke()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        biljkeView=findViewById(R.id.biljkeRV);
        biljkeView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        //dodavanje opcija u spinner
        val opcije = listOf("Medicinski", "Kuharski", "Botanički")
        //adapter za popunjavanje
        val spinner_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcije)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerRV=findViewById<Spinner>(R.id.modSpinner)
        spinnerRV.adapter=spinner_adapter

        biljkeAdapter1=BiljkaAdapterMedicinska(listOf())
        biljkeAdapter2= BiljkaAdapterBotanicka(listOf())
        biljkeAdapter3=BiljkaAdapterKuharska(listOf())

        spinnerRV.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption: String = parent?.getItemAtPosition(position).toString()
                if(selectedOption=="Medicinski")
                {
                    biljkeView.adapter=biljkeAdapter1
                    if (filtriranaLista.isNotEmpty()) {
                        biljkeAdapter1.updateBiljke(filtriranaLista)
                    }else
                    biljkeAdapter1.updateBiljke(biljke)
                }else if(selectedOption=="Botanički")
                {
                    biljkeView.adapter=biljkeAdapter2
                    if (filtriranaLista.isNotEmpty()) {
                        biljkeAdapter2.updateBiljke(filtriranaLista)
                    }else
                    biljkeAdapter2.updateBiljke(biljke)
                }else
                {
                    biljkeView.adapter=biljkeAdapter3
                    if (filtriranaLista.isNotEmpty()) {
                        biljkeAdapter3.updateBiljke(filtriranaLista)
                    }else
                    biljkeAdapter3.updateBiljke(biljke)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                biljkeAdapter1=BiljkaAdapterMedicinska(listOf())
                biljkeView.adapter=biljkeAdapter1
                biljkeAdapter1.updateBiljke(biljke)
            }
        }
        //ovdje dodajemo listener za svaki adapter posebno kad se klikne u modu
        //i pored toga se spasi filtrirana lista
        biljkeAdapter1.setOnItemClickListener(object : BiljkaAdapterMedicinska.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                filtriranaLista=biljkeAdapter1.filterByCriteria(biljka)
            }
        })

        biljkeAdapter2.setOnItemClickListener(object : BiljkaAdapterBotanicka.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                filtriranaLista=biljkeAdapter2.filterByCriteria(biljka)
            }
        })

        biljkeAdapter3.setOnItemClickListener(object : BiljkaAdapterKuharska.OnItemClickListener {
            override fun onItemClick(biljka: Biljka) {
                filtriranaLista=biljkeAdapter3.filterByCriteria(biljka)
            }
        })
        var resetButton:Button;
        resetButton=findViewById(R.id.resetBtn)
        resetButton.setOnClickListener {
            biljkeAdapter1.updateBiljke(biljke)
            biljkeAdapter2.updateBiljke(biljke)
            biljkeAdapter3.updateBiljke(biljke)
            filtriranaLista= emptyList()
        }
    }
}