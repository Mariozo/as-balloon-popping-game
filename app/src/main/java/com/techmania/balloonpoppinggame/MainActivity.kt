package com.techmania.balloonpoppinggame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.techmania.balloonpoppinggame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityMainBinding
    var score = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var   ballonsArray: Array<ImageView>
    private lateinit var mediaPlayer: MediaPlayer
    private var status = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.volume){

                if (!status){
                    mediaPlayer.setVolume(0F,0F)
                    it.setIcon(R.drawable.volume_off)
                    status = true
                }else{
                    mediaPlayer.setVolume(1F,1F)
                    it.setIcon(R.drawable.volume_up)
                    status = false
                }

                return@setOnMenuItemClickListener true

            }else{
                return@setOnMenuItemClickListener false
            }

        }

        mediaPlayer = MediaPlayer.create(this,R.raw.balloon_sound)

        ballonsArray = arrayOf(
            mainBinding.balloon1,
            mainBinding.balloon2,
            mainBinding.balloon3,
            mainBinding.balloon4,
            mainBinding.balloon5,
            mainBinding.balloon6,
            mainBinding.balloon7,
            mainBinding.balloon8,
            mainBinding.balloon9
        )

        object : CountDownTimer(5000,1000){
            override fun onTick(millisUntilFinished: Long) {

                mainBinding.textViewTimer.text = (millisUntilFinished / 1000).toString()

            }

            override fun onFinish() {

                balloonControl()

                object : CountDownTimer(30000,1000){
                    override fun onTick(millisUntilFinished: Long) {
                        val remainingSeconds = millisUntilFinished / 1000
                        mainBinding.textViewRemainingTime.text = getString(R.string.remaining_time, remainingSeconds)
                        /* mainBinding.textViewRemainingTime.text = "@string/Remaining Time: ${millisUntilFinished/1000}"*/
                    }

                    override fun onFinish() {

                        val intent = Intent(this@MainActivity,ResultActivity::class.java)
                        intent.putExtra("score",score)
                        startActivity(intent)
                        finish()
                    }


                }.start()

            }


        }.start()

    }

    fun increaseScoreByOne(view : View){
        score++
        /*   mainBinding.textViewScore.text = "Score: $score"*/
        mainBinding.textViewScore.text = getString(R.string.score_text,score)
        if (mediaPlayer.isPlaying){
            mediaPlayer.seekTo(0)
            mediaPlayer.start()
        }else{
            mediaPlayer.start()
        }

        when(view.id){

            mainBinding.balloon1.id -> mainBinding.balloon1.setImageResource(R.drawable.boom)
            mainBinding.balloon2.id -> mainBinding.balloon2.setImageResource(R.drawable.boom)
            mainBinding.balloon3.id -> mainBinding.balloon3.setImageResource(R.drawable.boom)
            mainBinding.balloon4.id -> mainBinding.balloon4.setImageResource(R.drawable.boom)
            mainBinding.balloon5.id -> mainBinding.balloon5.setImageResource(R.drawable.boom)
            mainBinding.balloon6.id -> mainBinding.balloon6.setImageResource(R.drawable.boom)
            mainBinding.balloon7.id -> mainBinding.balloon7.setImageResource(R.drawable.boom)
            mainBinding.balloon8.id -> mainBinding.balloon8.setImageResource(R.drawable.boom)
            mainBinding.balloon9.id -> mainBinding.balloon9.setImageResource(R.drawable.boom)

        }

    }

    fun balloonControl(){

        mainBinding.textViewTimer.visibility = View.INVISIBLE
        mainBinding.textViewRemainingTime.visibility = View.VISIBLE
        mainBinding.textViewScore.visibility = View.VISIBLE


        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {

            for (balloon in ballonsArray){
                balloon.visibility = View.INVISIBLE
                balloon.setImageResource(R.drawable.balloon)
            }
            mainBinding.gridLayout.visibility = View.VISIBLE
            val i = Random.nextInt(ballonsArray.size)
            ballonsArray[i].visibility = View.VISIBLE

            if (score <= 5){
                handler.postDelayed(runnable,2000)
            }
            if (score in 6..10){
                handler.postDelayed(runnable,1500)
            }
            if (score in 11..15){
                handler.postDelayed(runnable,1000)
            }
            if (score > 15){
                handler.postDelayed(runnable,500)
            }



        }
        handler.post(runnable)


    }

}