package com.example.kangning.basecapsule

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kangning.basecapsule.keyboard.KeyboardHeightObserver
import com.example.kangning.basecapsule.keyboard.KeyboardHeightProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleOwner, KeyboardHeightObserver {

    private lateinit var keyboardHeightProvider: KeyboardHeightProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        keyboardHeightProvider = KeyboardHeightProvider(this)

        // make sure to start the keyboard height provider after the onResume
        // of this activity. This is because a popup window must be initialised
        // and attached to the activity root view.
        val view = activitylayout
        view.post { keyboardHeightProvider.start() }
        camera_scanner.attachLifecycle(lifecycle)
        camera_scanner.attachedActivity = this
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider.setKeyboardHeightObserver(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
        keyboardHeightProvider.close()
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        camera_scanner.onKeyBoardHeight(height)
    }
}
