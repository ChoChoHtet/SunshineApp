package com.android.sunshineapp.view.ui

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.android.sunshineapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
      const  val EXTRA_REPLY= "com.android.sunshineapp.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        btnSave.setOnClickListener {
            val replyIntent= Intent()
            if(TextUtils.isEmpty(edText.text)){
                setResult(Activity.RESULT_CANCELED,replyIntent)
            }else{
                val word=edText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY,word)
                setResult(Activity.RESULT_OK,replyIntent)
                Log.e("Detail",edText.text.toString())
            }
            finish()
        }
    }

}
