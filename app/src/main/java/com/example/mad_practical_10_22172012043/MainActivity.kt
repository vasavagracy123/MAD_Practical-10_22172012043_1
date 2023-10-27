package com.example.mad_practical_10_22172012043

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
fun setpersondataListview(){
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val data = HttpRequest().makeServiceCall(
                "https://api.json-generator.com/templates/qjeKFdjkXCdK/data",
                "rbn0rerl1k0d3mcwgw7dva2xuwk780z1hxvyvrb1")
            withContext(Dispatchers.Main) {
                try {
                    if(data != null)
                        runOnUiThread{getPersonDetailsFromJson(data)}
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun getPersonDetailsFromJson(sJson: String?) {
    val personList = ArrayList<Person>()
    try {
        val jsonArray = JSONArray(sJson)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray[i] as JSONObject
            val person = Person(jsonObject)
            personList.add(person)
        }

        listView1.adapter = personAdapter(this, personList)
    } catch (ee: JSONException) {
        ee.printStackTrace()
    }
}
