package com.gmail.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.gmail.newsapp.NewsListAdapter

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var madapter:NewsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerView)

        //Firstly set up the layout manager of the recycler view.
        //Layout manager manages the layout of the news which are going to show on screen, like linear layout and grid layout, etc.
        rv.layoutManager = LinearLayoutManager(this)

        //now after setting up the layout manager, we have to set up the adapter and create a array list of strings to pass with it
        // for getting the ArrayList<String>, we create a function fetchdata(), who return the ArrayList<String>.
        fetchdata()
        madapter = NewsListAdapter(this)

        // now we have to link this adapter to the recyclerView ( rv ).
        rv.adapter = madapter


    }

private fun fetchdata(){
   val url = "https://saurav.tech/NewsAPI/top-headlines/category/entertainment/in.json"

    val jsonObjectRequest = JsonObjectRequest(
        //we don't have to send anything, that's why we had put null over the JSON request
        Request.Method.GET, url,null,
        {
           val newsjsonArray = it.getJSONArray("articles")
            val newsArray = ArrayList<News>()
            for ( i in 0 until newsjsonArray.length()){
                //val newsJsonObject = newsjsonArray[i]
                val newsJsonObject = newsjsonArray.getJSONObject(i)
                val news = News(
                    newsJsonObject.getString("title"),
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                )
                newsArray.add(news)
            }
            madapter.updateNews(newsArray)

        },
        {

        }

    )

    //its a recommended way to create a singleton( single ) class to handle the the requests.
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}

    override fun onItemClicked(item: News) {
        val url = item.url
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent  = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}