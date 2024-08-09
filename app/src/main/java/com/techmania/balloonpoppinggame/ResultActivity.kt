package com.techmania.balloonpoppinggame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.techmania.balloonpoppinggame.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var resultBinding : ActivityResultBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)

        val myScore = intent.getIntExtra("score",0)
        resultBinding.textViewMyScore.text = getString(R.string.score_text,myScore)

        sharedPreferences = this.getSharedPreferences("Score",Context.MODE_PRIVATE)
        val highestScore = sharedPreferences.getInt("highestScore",0)

        if (myScore >= highestScore){
            sharedPreferences.edit().putInt("highestScore",myScore).apply()
            resultBinding.textViewHighestScore.text = getString(R.string.score_highest,myScore)
            resultBinding.textViewInfo.text = getString(R.string.new_high_score_message)
        }else{

            if ((highestScore - myScore) > 10){
                resultBinding.textViewInfo.text = getString(R.string.faster_message)
            }else if ((highestScore - myScore) in 4..10){
                resultBinding.textViewInfo.text = getString(R.string.good_faster_message)
            }else{
                resultBinding.textViewInfo.text = getString(R.string.excellent_faster_message)
            }
        }

        resultBinding.buttonPlayAgain.setOnClickListener {

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        resultBinding.buttonQuit.setOnClickListener {

            finishAffinity()
        }
    }
}