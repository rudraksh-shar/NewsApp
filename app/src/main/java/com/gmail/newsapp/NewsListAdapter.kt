package com.gmail.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//Website: -
// 1) https://dev.to/raulmonteroc/recyclerview-basics-part-2-adapters-and-viewholder-36ek
// 2) https://www.geeksforgeeks.org/how-to-create-recyclerview-with-multiple-viewtype-in-android/

//the xml file "item_news" is a layout file which is like a template for the layout of individual viewholders
 class NewsListAdapter( private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>( ) {

    private val items:ArrayList<News> = ArrayList()
    //whenever a ViewHolder is created, this function will get called and it will convert or inflate the activity_main
    //file into a View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        // what is this context ?
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news ,parent,false)
        val viewholder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun getItemCount(): Int {
       return items.size
    }

    //It binds the item( present in the given "ArrayList<String>" ) to the ViewHolder.
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentItem = items[position]
        holder.titleview.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageurl).into(holder.imageview)

    }
    //Now to give the array of news to adapter, we need to create a function
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged() // after this function call, all the functions of adapter will again
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleview:TextView = itemView.findViewById(R.id.title)
    val imageview:ImageView = itemView.findViewById(R.id.image)
    val author:TextView = itemView.findViewById(R.id.author)

}

interface NewsItemClicked{
    fun onItemClicked (item:News)
}
