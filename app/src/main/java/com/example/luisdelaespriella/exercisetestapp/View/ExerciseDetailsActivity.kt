package com.example.luisdelaespriella.exercisetestapp.View

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.example.luisdelaespriella.exercisetestapp.Model.Exercise
import com.example.luisdelaespriella.exercisetestapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlinx.android.synthetic.main.activity_exercise_details.*

class ExerciseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_details)
        Log.d("ExerciseDetailsActivity", "Inside ExerciseDetailsActivity")
        this.getIncomingIntent()
    }

    private fun getIncomingIntent() {
        Log.d("getIncomingIntent", "Inside getIncomingIntent")
        if (intent.hasExtra("exercise_id") && intent.hasExtra("exercise_image")) {
            val exerciseId: Int = intent.getIntExtra("exercise_id", 0)
            val image: Bitmap = intent.getParcelableExtra("exercise_image")

            getExerciseById(exerciseId, image)
        }
    }

    private fun getExerciseById(id: Int, image: Bitmap) {
        Log.d("getExerciseById", "id: $id")
        doAsync {

            val jsonExercise = URL("https://wger.de/api/v2/exercise/$id/").readText()
            val exercise: Exercise = Gson().fromJson(jsonExercise, object: TypeToken<Exercise>(){}.type)

            uiThread {
                Glide.with(it)
                        .asBitmap()
                        .load(image)
                        .into(image_exercise_image)
                tv_exercise_name.text = exercise.name
                tv_exercise_description.text = exercise.description
            }
        }
    }
}