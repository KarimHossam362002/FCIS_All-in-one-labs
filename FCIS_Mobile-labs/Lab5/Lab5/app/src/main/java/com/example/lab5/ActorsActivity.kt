package com.example.lab5
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActorsActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    private lateinit var actorName: EditText

    private lateinit var btnAddActor: Button

    private lateinit var btnBack: Button

    private lateinit var db: MovieDatabase

    private var movieId: Int = 0

    private var actors: MutableList<Actor> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_actor)

        listView = findViewById( R.id.list_view_actors)
        actorName = findViewById( R.id.edit_text_actor_name)
        btnAddActor = findViewById( R.id.button_add_actor)
        btnBack = findViewById( R.id.btn_back)

        db = MovieDatabase.getInstance(this)
        movieId = intent.getIntExtra( "movieId",  0)
        loadActors()

        btnAddActor.setOnClickListener {
            val name = actorName.text.toString().trim()
            if (name.isNotEmpty()) {
                lifecycleScope.launch( Dispatchers.IO) {
                    db.actorDao().insertActor(Actor(movieId = movieId, name = name))
                    withContext( Dispatchers.Main) {
                        Toast.makeText(this@ActorsActivity, "Actor added!",  Toast.LENGTH_SHORT).show()
                        actorName.text.clear()
                        loadActors()
                    }
                }
            } else {
                Toast.makeText(this, "Enter actor name",
                     Toast.LENGTH_SHORT).show()
            }
        }


        btnBack.setOnClickListener { finish() }
    }
    private fun loadActors() {
        lifecycleScope.launch(context = Dispatchers.IO) {
            actors = db.actorDao().getActorsForMovie(movieId).toMutableList()

            withContext(context = Dispatchers.Main) {
                val adapter = ArrayAdapter(this@ActorsActivity, android.R.layout.simple_list_item_1, actors.map { it.name }
                )
                listView.adapter = adapter
            }
        }
    }
}
