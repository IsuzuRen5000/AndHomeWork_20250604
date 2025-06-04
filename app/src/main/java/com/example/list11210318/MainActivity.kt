package com.example.list11210318


import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.list11210318.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val targetUrl = "https://data.moenv.gov.tw/api/v2/stat_p_122?language=zh&offset=10&limit=1000&api_key=9a3fe0d7-77a8-4d19-af97-58dfa18460dc"
    private var GetString=""
    private var mList = ArrayList<DataSite>()
    private lateinit var adapter: SiteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.Recycler.layoutManager= LinearLayoutManager(this)
        binding.button.setOnClickListener {
            val client = OkHttpClient.Builder().build()
            val request = Request.Builder().url(targetUrl).build()
            var s = ""

            GlobalScope.launch {
                runBlocking(Dispatchers.IO) {
                    var response = client.newCall(request).execute()
                    response.body.run {
                        GetString = string()
                        try {
                            s = GetString.toString()
                            var allData = JSONObject(s)
                            var getRecored = allData.getJSONArray("records")
                            for (i in 0..getRecored.length()) {
                                var getOneObject = getRecored.getJSONObject(i)
                                mList.add(
                                    DataSite(
                                        getOneObject.getString("item1"),
                                        getOneObject.getString("item2"),
                                        getOneObject.getString("value1"),
                                        getOneObject.getString("value2"),
                                    )
                                )
                            }

                        } catch (e: JSONException) {
                            Log.d("myTag", "Error : $e")
                        }
                    }
                }
                runOnUiThread {
                    binding.showMsg.text = s
                    adapter = SiteAdapter(mList)
                    binding.Recycler.adapter = adapter
                }
            }

        }
    }
}