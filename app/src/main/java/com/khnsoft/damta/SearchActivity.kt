package com.khnsoft.damta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.khnsoft.damta.data.Place
import com.khnsoft.damta.utils.KakaoAPI
import com.khnsoft.damta.utils.MyLogger
import kotlinx.android.synthetic.main.activity_search.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class SearchActivity : AppCompatActivity() {
    var keyword = ""

    companion object {
        const val KAKAO_URL = "http://dapi.kakao.com"
        const val CONNECTION_TIMEOUT = 3000
        const val REST_KEY = "114208e593c69cfcbf5cc396f9e83661"

        const val RC_ADDRESS = 100
        const val EXTRA_ADDRESS = "address"
        const val EXTRA_X = "x"
        const val EXTRA_Y = "y"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btn_back.setOnClickListener {
            finish()
        }

        btn_search.setOnClickListener {
            keyword = et_search.text.toString()
            val searchThread = Thread(searchRunnable)
            searchThread.start()
        }

        btn_clear.setOnClickListener {
            et_search.text.clear()
        }
    }

    val searchRunnable = Runnable {
        val query = "query=${keyword}"

        val result1 = getResultFromAPI(KakaoAPI.SEARCH_PLACE, query)
        val result2 = getResultFromAPI(KakaoAPI.SEARCH_ADDRESS, query)

        val lPlace = ArrayList<Place>()
        if (result1?.has("documents") == true) {
            val lResult = result1["documents"].asJsonArray
            for (item in lResult) {
                val jPlace = item.asJsonObject
                lPlace.add(
                    Place(
                        name = if (!jPlace["place_name"].isJsonNull) jPlace["place_name"].asString else "",
                        address = jPlace["address_name"].asString,
                        roadAddress = jPlace["road_address_name"].asString,
                        x = jPlace["x"].asDouble,
                        y = jPlace["y"].asDouble
                    )
                )
            }
        }
        if (result2?.has("documents") == true) {
            val lResult = result2["documents"].asJsonArray
            for (item in lResult) {
                val jPlace = item.asJsonObject
                lPlace.add(
                    Place(
                        name = jPlace["address_name"].asString,
                        address = if (!jPlace["address"].isJsonNull) jPlace["address"].asJsonObject["address_name"].asString else "",
                        roadAddress = if (!jPlace["road_address"].isJsonNull) jPlace["road_address"].asJsonObject["address_name"].asString else "",
                        x = jPlace["x"].asDouble,
                        y = jPlace["y"].asDouble
                    )
                )
            }
        }

        runOnUiThread {
            val adapter = AddressRecyclerAdapter(lPlace, keyword)
            val lm = LinearLayoutManager(this@SearchActivity)
            search_container.layoutManager = lm
            search_container.adapter = adapter
        }
    }

    inner class AddressRecyclerAdapter(val lPlace: ArrayList<Place>, val keyword: String) :
        RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val container = itemView.findViewById<LinearLayout>(R.id.item_container)
            val highlight = itemView.findViewById<TextView>(R.id.tv_highlight)
            val normal = itemView.findViewById<TextView>(R.id.tv_normal)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(this@SearchActivity).inflate(R.layout.item_search, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lPlace.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val place = lPlace[position]
            val idx: Int
            val highlight: String
            val normal: String
            val name1 = place.name
            val name2 = place.address
            val name3 = place.roadAddress

            if (name1.indexOf(keyword) != -1) {
                idx = name1.indexOf(keyword)
                highlight = name1.substring(idx, idx + keyword.length)
                normal = name1.substring(idx + keyword.length)
            } else if (name2.indexOf(keyword) != -1) {
                idx = name2.indexOf(keyword)
                highlight = name2.substring(idx, idx + keyword.length)
                normal = name2.substring(idx + keyword.length)
            } else  if (name3.indexOf(keyword) != -1) {
                idx = name3.indexOf(keyword)
                highlight = name3.substring(idx, idx + keyword.length)
                normal = name3.substring(idx + keyword.length)
            } else {
                if (!name1.isBlank()) {
                    highlight = ""
                    normal = name1
                } else if (!name2.isBlank()) {
                    highlight = ""
                    normal = name2
                } else {
                    highlight = ""
                    normal = name3
                }
            }

            holder.highlight.text = highlight
            holder.normal.text = normal

            holder.container.setOnClickListener {
                val intent = Intent()
                intent.putExtra(EXTRA_ADDRESS, if (!place.address.isBlank()) place.address else place.roadAddress)
                intent.putExtra(EXTRA_X, place.x)
                intent.putExtra(EXTRA_Y, place.y)
                setResult(RC_ADDRESS, intent)
                finish()
            }
        }
    }

    private fun getResultFromAPI(api: KakaoAPI, query: String): JsonObject? {
        val method = api.method

        var ret: String? = null
        var httpConn: HttpURLConnection? = null

        try {
            val url = URL("${KAKAO_URL}${api.remote}?${query}")
            httpConn = url.openConnection() as HttpURLConnection

            httpConn.requestMethod = method.name
            httpConn.connectTimeout = CONNECTION_TIMEOUT
            httpConn.setRequestProperty("Content-Type", "application/json")
            httpConn.setRequestProperty("Accept", "application/json")
            httpConn.setRequestProperty("Authorization", "KakaoAK ${REST_KEY}")
            httpConn.doInput = true

            MyLogger.d("KakaoInfo", "URL: ${url}, API: ${api.remote}, METHOD: ${method.name}")

            val status = httpConn.responseCode
            try {
                val inputStream = if (status != HttpURLConnection.HTTP_OK) httpConn.errorStream
                else httpConn.inputStream

                if (inputStream != null) {
                    ret = convertInputStreamToString(inputStream)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpConn?.disconnect()
        }

        return JsonParser.parseString(ret ?: return null).asJsonObject
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var ret = ""

        var line = bufferedReader.readLine()
        while (line != null) {
            ret += line
            line = bufferedReader.readLine()
        }
        inputStream.close()
        return ret
    }
}