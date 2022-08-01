package org.japancv.multiplescreens

import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.japancv.multiplescreens.databinding.ActivityMainBinding
import java.util.concurrent.atomic.AtomicInteger

/**
 * The first activity is displayed.
 * You can display the second screen by tapping the button
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = AtomicInteger(0)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // launch the second screen if the device has.
        launchSecondScreen()

        binding.btnCommunicateWithSecondScreen.setOnClickListener {
            launchSecondScreen()
        }
    }

    /**
     * Navigate to the second screen.
     *
     * See more at https://developer.android.com/guide/topics/large-screens/multi-window-support
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun launchSecondScreen() {
        // DisplayManager manages the properties of attached displays.
        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        // List displays was attached
        val displays = displayManager.displays

        if (displays.size > 1) {
            logDisplayInfo(TAG, displays[0])

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // To display on the second screen that your intent must be set flag to make
                // single task or single instance (combine FLAG_ACTIVITY_CLEAR_TOP and FLAG_ACTIVITY_NEW_TASK)
                // or you also set it in the manifest (see more at the manifest file)
                val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                    putExtra(EXTRA_COUNTER, counter.getAndIncrement())
                }
                if (activityManager.isActivityStartAllowedOnDisplay(this, displays[1].displayId, intent)) {
                    // Activity options are used to select the display screen.
                    val options = ActivityOptions.makeBasic()
                    // Select the display screen that you want to show the second activity
                    options.launchDisplayId = displays[1].displayId
                    startActivity(
                        intent,
                        options.toBundle()
                    )

                    binding.btnCommunicateWithSecondScreen.visibility = View.VISIBLE
                }
            }
        } else {
            Toast.makeText(this, "Not found the second screen", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val TAG = "MainActivity"
        private const val EXTRA_COUNTER = "EXTRA_COUNTER"
    }
}
