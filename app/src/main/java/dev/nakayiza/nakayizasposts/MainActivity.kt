package dev.nakayiza.nakayizasposts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPosts()
    }
    fun getPosts(){
        val retrofit=APIClient.buildApiClient(APIInterface::class.java)
        val request=retrofit.getPosts()

        var RecyclerView=findViewById<RecyclerView>(R.id.rvposts)
            request.enqueue(object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful){
                        val posts=response.body()
                        Toast.makeText(applicationContext,"fetched ${posts!!.size} posts",
                            Toast.LENGTH_LONG).show()
                        RecyclerView.apply {
                            layoutManager=LinearLayoutManager(this@MainActivity)
                            adapter=PostAdapter(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
