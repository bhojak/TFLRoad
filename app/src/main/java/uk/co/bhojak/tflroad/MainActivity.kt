package uk.co.bhojak.tflroad


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uk.co.bhojak.tflroad.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Set the focus to the edit text.
        binding.edReadData.requestFocus()
        binding.btnGetData.setOnClickListener {
            updateData(it)
        }
        subscribe()
    }

    /**
     * Click handler for the Done button.
     */
    private fun updateData(view: View) {
        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        if (isOnline(this)) {
            val roadName = binding.edReadData.text.toString()
            if (roadName.length > 1) {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getDataFromServer(roadName)
            } else {
                // display error message
                binding.txtSearchDesc.setText(R.string.search_hint)
            }
        } else {
            // display Network error message
            binding.txtSearchDesc.setText(R.string.network_error)
        }
    }

    private fun subscribe() {
        val serverDataObserver: Observer< in Road> =
            Observer { road ->
                binding.progressBar.visibility = View.GONE
                if (road.error == "no") {
                    val roadName = "\n" + getString(R.string.road_name) + " = " + road.name
                    val status = "\n" + getString(R.string.road_status) + " = " + road.status
                    val desc = "\n" + getString(R.string.road_status_desc) + " = " + road.desc
                    binding.txtSearchDesc.text = roadName + status + desc
                } else {
                    val roadName = "\n" + getString(R.string.road_name) + " = " + road.name
                    val error = "\n" + getString(R.string.road_status) + " = " + road.error
                    binding.txtSearchDesc.text = error + roadName
                }

            }
        viewModel.getCurrentRoadStatus()!!.observe(this, serverDataObserver)
    }



    //returns internet connection
    private fun isOnline(context: Context): Boolean {
        var isOnline = false
        try {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                val netInfo = cm.activeNetworkInfo
                //should check null because in airplane mode it will be null
                isOnline = netInfo != null && netInfo.isConnected
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return false
        }
        return isOnline
    }

}
