package com.example.luisdelaespriella.exercisetestapp.View

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import com.example.luisdelaespriella.exercisetestapp.Model.Exercise
import com.example.luisdelaespriella.exercisetestapp.Model.Results
import com.example.luisdelaespriella.exercisetestapp.R
import com.example.luisdelaespriella.exercisetestapp.R.menu.menu_items
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var mExercises: MutableList<Exercise> = mutableListOf()
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllExercises(this)
    }

    private fun getAllExercises(activity: MainActivity) {
        doAsync {
            mExercises = getDataFromApi()

            uiThread {
                // Initialize recycler view
                rv_exercise_list.layoutManager = LinearLayoutManager(activity)
                adapter = ExerciseAdapter(mExercises, activity)
                rv_exercise_list.adapter = adapter
            }
        }

    }

    private fun getDataFromApi(): MutableList<Exercise> {
        val jsonResults = URL("https://wger.de/api/v2/exercise?limit=10").readText()

        val results: Results = Gson().fromJson(jsonResults, object: TypeToken<Results>(){}.type)

        val filteredList: MutableList<Exercise> = results.Exercises.filterNot { it.name == ""} as MutableList<Exercise>

        for(item in filteredList) {
            item.image = BitmapFactory.decodeStream(URL("https://picsum.photos/200/200/?random").openStream())
        }

        return filteredList
    }

    override fun onCreateOptionsMenu (menu: Menu): Boolean {
        menuInflater.inflate(menu_items, menu)
        val menuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    // Called when user inputs text
    override fun onQueryTextChange(newText: String?): Boolean {

        var newList: MutableList<Exercise> = mExercises.filter { it.name.contains(StringBuilder(newText), true) } as MutableList<Exercise>

        adapter.setFilter(newList)

        return true
    }
}
