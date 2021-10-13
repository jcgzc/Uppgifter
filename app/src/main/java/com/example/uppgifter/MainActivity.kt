package com.example.uppgifter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        val db = DatabaseHandler(context)


        btn_reg.setOnClickListener {
            if (inputName.text.toString().isNotEmpty() && inputAge.text.toString().isNotEmpty() && inputGender.text.toString().isNotEmpty()){

                val students = Students(inputName.text.toString(), inputAge.text.toString().toInt(), inputGender.text.toString())

                db.insertData(students)
                clearField()
            } else {

                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }

        }

        btn_show.setOnClickListener {
            val data = db.readData()
            textView.text = ""
            for( i in 0 until data.size){
                textView.append(
                    "id:" + data[i].id.toString() + "\nname: " + data[i].name + "\nage:" + data[i].age.toString() + "\ngender:" + data[i].gender +"\n"
                )
            }

        }

    }

    private fun clearField() {
        inputName.text.clear()
        inputAge.text.clear()
        inputGender.text.clear()
    }
}