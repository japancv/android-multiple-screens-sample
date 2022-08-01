package org.japancv.multiplescreens

import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.japancv.multiplescreens.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays
        if (displays.size > 1) {
            logDisplayInfo(TAG, displays[1])
        }

        // Show the Intent data when this activity is initiated
        showIntentData(intent)
    }

    /**
     * Since SecondActivity's launch mode should be SingleTask or SingleInstance, once this Activity has been initiated
     * the Intent would be pass through this callback.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        showIntentData(intent)
    }

    private fun showIntentData(intent: Intent?) {
        intent?.let {
            binding.textviewCounter.text = it.getIntExtra(EXTRA_COUNTER, -1).toString()
        }
    }

    companion object {
        private const val TAG = "SecondActivity"
        private const val EXTRA_COUNTER = "EXTRA_COUNTER"
    }
}
