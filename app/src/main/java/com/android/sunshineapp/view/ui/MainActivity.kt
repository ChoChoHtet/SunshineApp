package com.android.sunshineapp.view.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.sunshineapp.InjectortUtils
import com.android.sunshineapp.R
import com.android.sunshineapp.data.database.WeatherEntry
import com.android.sunshineapp.view.adapter.WeatherAdapter
import com.android.sunshineapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel:MainActivityViewModel
    companion object {
        const val networkActivityRequestCode=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.setHasFixedSize(true)
        recycler.layoutManager=LinearLayoutManager(this)
        adapter=WeatherAdapter(this)
        recycler.adapter=adapter

        btnNext.setOnClickListener {
            val intent=Intent(this@MainActivity,DetailActivity::class.java)
           startActivityForResult(intent, networkActivityRequestCode)
        }

        val factory=InjectortUtils.provideMainViewModelFactory(this.applicationContext)
         viewModel=ViewModelProviders.of(this,factory).get(MainActivityViewModel::class.java)
        /* viewModel.getCurrentData().observe(this, Observer {words ->
           words?.let {
               adapter.setWord(it)

           }
        })*/
        viewModel.getAllWords().observe(this, Observer {words->
            words?.let {
                adapter.setWord(it)
            }

        })
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode== networkActivityRequestCode && resultCode== Activity.RESULT_OK){
            data?.let {
                val word=WeatherEntry(it.getStringExtra(DetailActivity.EXTRA_REPLY))
                viewModel.insertWord(word)
            }
        }else{
            Log.e("MainActivity","Save data failed")
        }
    }*/
}
