package com.example.ponggamemf
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Bundle


class MainActivity : Activity() {

    private lateinit var pongView: PongView

    var topScoreRetrieved = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val size = Point()
        pongView = PongView(this, size.x, size.y)
        pongView.topScore = topScoreRetrieved
        setContentView(pongView)
    }

    override fun onResume() {
        super.onResume()

        // Retrieving topscore
        val sharedPref = this.getSharedPreferences(Context.MODE_PRIVATE)
        topScoreRetrieved = sharedPref.getInt("top", 0)
        pongView.topScore = topScoreRetrieved

        //function from pongView klassen
        pongView.resume()
    }

    override fun onPause() {
        super.onPause()

        //saving topscore
        val sharedPref = this.getSharedPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (pongView.topScore <= pongView.score){
            if (topScoreRetrieved < pongView.score) editor.putInt("top", pongView.score)
        }else {
            if (topScoreRetrieved < pongView.topScore) editor.putInt("top", pongView.topScore)
        }
        editor.apply()

        //function from pongView klassen
        pongView.pause()
    }
}