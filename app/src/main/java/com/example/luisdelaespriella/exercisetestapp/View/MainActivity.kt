package com.example.luisdelaespriella.exercisetestapp.View

import android.database.Observable
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.luisdelaespriella.exercisetestapp.Model.Exercise
import com.example.luisdelaespriella.exercisetestapp.Model.Results
import com.example.luisdelaespriella.exercisetestapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllExercises(this)
    }

    private fun getAllExercises(activity: MainActivity) {
        doAsync {
            var exercises = getDataFromApi()

            uiThread {
                rv_exercise_list.layoutManager = LinearLayoutManager(activity)
                rv_exercise_list.adapter = ExerciseAdapter(exercises, activity)
            }
        }

    }

    private fun getDataFromApi(): List<Exercise> {
        val jsonResults = URL("https://wger.de/api/v2/exercise?limit=10").readText()

        val results: Results = Gson().fromJson(jsonResults, object: TypeToken<Results>(){}.type)

        val filteredList: List<Exercise> = results.Exercises.filterNot { it.name == ""}

        for(item in filteredList) {
            item.image = BitmapFactory.decodeStream(URL("https://picsum.photos/200/200/?random").openStream())
        }

        return filteredList
    }
}
