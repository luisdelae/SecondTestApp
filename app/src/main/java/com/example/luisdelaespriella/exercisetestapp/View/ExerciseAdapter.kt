@file:Suppress("PackageName")

package com.example.luisdelaespriella.exercisetestapp.View

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.luisdelaespriella.exercisetestapp.Model.Exercise
import com.example.luisdelaespriella.exercisetestapp.R
import kotlinx.android.synthetic.main.exercise_list_item.view.*

class ExerciseAdapter(private var mItems: MutableList<Exercise>, private val context: Context) :
        RecyclerView.Adapter<ExerciseViewHolder>() {

    // Gets number of animals in the list
    override fun getItemCount(): Int {
        return mItems.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.exercise_list_item, parent, false)
        )
    }

    // Binds each exercise in the ArrayList to a view
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.tvExercise.text = mItems[position].name

        // Puts the image in the image viewer
        Glide.with(context)
                .asBitmap()
                .load(mItems[position].image)
                .into(holder.dExerciseImage)

        // Do something when the row is clicked
        holder.itemView.setOnClickListener({
            val intent = Intent(context, ExerciseDetailsActivity::class.java)
            intent.putExtra("exercise_id", mItems[position].id)
            intent.putExtra("exercise_image", mItems[position].image)
            context.startActivity(intent)
        })

    }

    fun setFilter(newList: MutableList<Exercise>) {
        mItems = mutableListOf()
        mItems.addAll(newList)

        Log.d("setFilter", mItems.toString())

        notifyDataSetChanged()

        // Refreshes the adapter
//        recyclerView.adapter.
    }
}

class ExerciseViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvExercise: TextView = view.tv_exercise
    val dExerciseImage: ImageView = view.d_weightlift
}