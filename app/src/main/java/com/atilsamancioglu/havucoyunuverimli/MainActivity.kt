package com.atilsamancioglu.havucoyunuverimli

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var skor = 0
    var koordinatListesi = ArrayList<Pair<Float,Float>>()
    var handler = Handler()
    var runnable = Runnable {  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Metrik Alma

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val uzunluk = displayMetrics.heightPixels.toFloat()
        val genislik = displayMetrics.widthPixels.toFloat()

        //Pair
        val koordinat1 = Pair(genislik/9,uzunluk/4)
        val koordinat2 = Pair(genislik/9 + genislik/6,uzunluk/4)
        val koordinat3 = Pair(genislik/9 + genislik/3,uzunluk/4)
        val koordinat4 = Pair(genislik/9,uzunluk/8)
        val koordinat5 = Pair(genislik/9,uzunluk/8 + uzunluk/4)
        val koordinat6 = Pair(genislik/9,uzunluk/8 + uzunluk/2)


        koordinatListesi.add(koordinat1)
        koordinatListesi.add(koordinat2)
        koordinatListesi.add(koordinat3)
        koordinatListesi.add(koordinat4)
        koordinatListesi.add(koordinat5)
        koordinatListesi.add(koordinat6)

        //CountDownTimer

        object : CountDownTimer(15000,1000){
            override fun onFinish() {
                zamanText.text = "Zaman: 0"

                handler.removeCallbacks(runnable)

                //Uyarı
                val uyari = AlertDialog.Builder(this@MainActivity)
                uyari.setTitle("Oyun Bitti")
                uyari.setMessage("Oynamak İster misin?")
                uyari.setPositiveButton("Evet",DialogInterface.OnClickListener { dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                })

                uyari.setNegativeButton("Hayır",DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this@MainActivity,"Oyun Bitti",Toast.LENGTH_LONG).show()
                })

                uyari.show()
            }

            override fun onTick(millisUntilFinished: Long) {
                zamanText.text = "Zaman: ${millisUntilFinished/1000}"
            }

        }.start()

        havucuHareketEttir()

    }


    fun havucuHareketEttir() {

        runnable = object : Runnable{
            override fun run() {
                val random = Random()
                val rastgeleIndex = random.nextInt(6)

                imageView.x = koordinatListesi[rastgeleIndex].first
                imageView.y = koordinatListesi[rastgeleIndex].second

                handler.postDelayed(runnable,500)
            }

        }

        handler.post(runnable)

    }


    fun skoruArttir(view: View){
        skor = skor + 1
        skorText.text = "Skor: ${skor}"
    }
}