package slipp.study.slipper

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import java.net.URL

data class ChocO(val id: Int, val name: String)

class BoardDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)

        val board = boards[intent.getLongExtra("board",1).toInt() - 1]

        val categoryName: TextView = findViewById(R.id.board_category_name)
        val boardTitle: TextView = findViewById(R.id.board_title)
        val boardRegister: TextView = findViewById(R.id.board_register)
        val boardRegistered: TextView = findViewById(R.id.board_registered)
        val boardImage: ImageView = findViewById(R.id.board_image)
        val boardContent: TextView = findViewById(R.id.board_content)

        categoryName.text = board.category.name
        boardTitle.text = board.title
        boardRegister.text = board.register
        boardRegistered.text = board.registered

        if(board.image?.isNotEmpty()) {
//            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//            boardImage.setImageBitmap(BitmapFactory.decodeStream(URL(board.image).openConnection().getInputStream()));
            Picasso.get().load(board.image).into(boardImage)
            Fuel.get("https://api.github.com/users/ChocOZerO/repos").responseJson { request, response, result ->
                println("request : " + request)
                println("response : " + response)
                println("result : " + result)
                val (bytes, error) = result
                if (bytes != null) {
                    println("bytes : " + bytes)
                    val datas = bytes.array()
                    println(datas.length())
                    for (index in 0..(datas.length()-1)) {
                        println(datas.getJSONObject(index).getString("name"))
                    }
                }
            }
        }

        boardContent.text = board.content

        val modify = findViewById<Button>(R.id.modify)

        if(!isSignedIn) {
            modify.visibility = View.GONE
        }

        modify.setOnClickListener {
            if(!isSignedIn) {
                intent = Intent(this, SignInActivity::class.java)
                startActivity(intent);
                return@setOnClickListener
            }

            intent = Intent(this, BoardModifyActivity::class.java)
            intent.putExtra("board", board.no)
            intent.putExtra("category", board.category.no)
            startActivity(intent);
        }

        val delete = findViewById<Button>(R.id.delete)

        if(!isSignedIn) {
            delete.visibility = View.GONE
        }

        delete.setOnClickListener {

            if(!isSignedIn) {
                intent = Intent(this, SignInActivity::class.java)
                startActivity(intent);
                return@setOnClickListener
            }

            boards.remove(board)

            Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_LONG).show()

            intent = Intent(this, CategoryTabActivity::class.java)
            intent.putExtra("category", board.category.no)
            startActivity(intent);
        }

        val commentButton = findViewById<Button>(R.id.comment)

        commentButton.setOnClickListener {

            if(!isSignedIn) {
                intent = Intent(this, SignInActivity::class.java)
                startActivity(intent);
                return@setOnClickListener
            }

            intent = Intent(this, BoardCommentActivity::class.java)
            intent.putExtra("board", board.no)
            startActivity(intent);
        }

        val commentAll = findViewById<TextView>(R.id.comment_all)

        commentAll.setOnClickListener {
            intent = Intent(this, BoardCommentActivity::class.java)
            intent.putExtra("board", board.no)
            startActivity(intent);
        }

        val commentQty = findViewById<TextView>(R.id.comment_qty)
        commentQty.text = CommentListAdapter(this, intent).count.toString()

        val commentListView: ListView = findViewById(R.id.comment_list)
        commentListView.adapter = CommentListAdapter(this, intent)
    }
}