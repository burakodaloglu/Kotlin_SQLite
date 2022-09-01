package com.burakkodaloglu.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakkodaloglu.sqlite.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context=this
        val db = DataBaseHelper(context)
        binding.btnkaydet.setOnClickListener {
            val etadsoyad=binding.etadsoyad.text.toString()
            val etyas=binding.etyas.text.toString()

            if(etadsoyad.isNotEmpty() && etyas.isNotEmpty()){
                val kullanici=Kullanici(etadsoyad,etyas.toInt())
                db.insertData(kullanici)
            }
            else{
                Toast.makeText(applicationContext,"Lütfen Boş Alanları Dolduralım", Toast.LENGTH_LONG).show()
            }
        }

        //Verileri okumak için
        binding.btnoku.setOnClickListener {
            val data = db.readData()
            binding.textView.text=""
            for (i in 0 until data.size){
            binding.textView.append(data.get(i).id.toString()+" "
            +data.get(i).adsoyad+ " " + data.get(i).yasi + "\n")
            }
        }

        //Verileri güncellemek için
        binding.btnguncelle.setOnClickListener {
            db.updateData()
            binding.btnoku.performClick()
        }

        //Verileri silmek için
        binding.btnsil.setOnClickListener {
            db.deleteData()
            binding.btnoku.performClick()
        }
    }
}