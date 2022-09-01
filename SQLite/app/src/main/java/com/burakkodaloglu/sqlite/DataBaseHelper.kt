package com.burakkodaloglu.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name="VeriTabani"
val table_name="Kullanicilar"
val col_name="adisoyadi"
val col_age="yasi"
val col_id="id"

class DataBaseHelper (var context: Context):SQLiteOpenHelper(context,
database_name,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //VeriTabanı birkez çalışır
        val createTable=" CREATE TABLE "+ table_name+"("+
                col_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_name +" VARCHAR(256),"+
                col_age +" INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //Veri Kaydetmek için Fonksiyon TAnımlıyoz
       fun insertData(kullanici: Kullanici) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, kullanici.adsoyad)
        cv.put(col_age, kullanici.yasi)
        val sonuc = db.insert(table_name, null, cv)

        if (sonuc == (-1).toLong()) {
            Toast.makeText(context, "Hatalı", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        }
    }

    //Veri okumak için Fonksiyon yazıyoruz
    @SuppressLint("Range")
    fun readData(): MutableList<Kullanici> {
        val liste:MutableList<Kullanici> = ArrayList()
        val db = this.readableDatabase
        val sorgu = "Select * from "+ table_name
        val sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do {
                val kullanici=Kullanici()
                kullanici.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kullanici.adsoyad = sonuc.getString(sonuc.getColumnIndex(col_name))
                kullanici.yasi = sonuc.getString(sonuc.getColumnIndex(col_age)).toInt()
                liste.add(kullanici)
            }
            while (sonuc.moveToNext())}
            sonuc.close()
            db.close()
            return liste
    }

    //Veri güncellemek için fonksiyon taınmlıyoruz
    @SuppressLint("Range")
    fun updateData(){
        val db = this.readableDatabase
        val sorgu = "Select * from $table_name"
        val sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do{
                val cv = ContentValues()
                cv.put(col_age,(sonuc.getInt(sonuc.getColumnIndex(col_age)))+1)
                cv.put(col_name,(sonuc.getString(sonuc.getColumnIndex(col_name)))+" "+"Güncellendi")
                db.update(table_name,cv,"$col_id=? AND $col_name=?",
                arrayOf(sonuc.getString(sonuc.getColumnIndex(col_id)),
                sonuc.getString(sonuc.getColumnIndex(col_name))))
            }while (sonuc.moveToNext())

        }
        sonuc.close()
        db.close()

    }

    fun deleteData(){
        val db = this.writableDatabase
        db.delete(table_name,null,null)
        db.close()
    }

}