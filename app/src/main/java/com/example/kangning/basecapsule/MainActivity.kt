package com.example.kangning.basecapsule

import android.arch.lifecycle.LifecycleOwner
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        camera_scanner.startScan().subscribe {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
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
//        camera_scanner.onKeyBoardHeight(height)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        camera_scanner.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
