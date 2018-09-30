package slipp.study.slipper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import slipp.study.slipper.CategoryAdapter
import slipp.study.slipper.CategoryTabActivity
import slipp.study.slipper.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gridView: GridView = findViewById(R.id.category)
        gridView.adapter = CategoryAdapter(this)

        gridView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    intent = Intent(this, CategoryTabActivity::class.java)
                    intent.putExtra("category", id)
                    startActivity(intent);
                }
    }
}
