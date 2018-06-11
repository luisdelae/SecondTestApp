package com.example.luisdelaespriella.exercisetestapp.Model

import android.graphics.Bitmap
import android.media.Image
import com.google.gson.annotations.SerializedName

data class Results(
        @SerializedName("results")
        val Exercises: List<Exercise>
)

data class Exercise(
        @SerializedName("id")
        val id: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("name")
        val name: String,
        var image: Bitmap
)