package uk.co.bhojak.tflroad

import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel : ViewModel() {

    private val mResultData = MutableLiveData<Road>()

    override fun onCleared() {
        super.onCleared()
    }

     fun getDataFromServer ( roadName: String) {
         // Get remote json
         val url: String = BuildConfig.BASE_URL + "Road/" + roadName +"?app_id="+BuildConfig.APP_ID+"&app_key="+BuildConfig.APP_KEY
         val scope = CoroutineScope(Dispatchers.Default)
         scope.launch {
             try {
                 val jsonResponse: String = getServerData(url)
                     if (notNull(jsonResponse)) {
                         var obj = JSONObject()
                         try {
                             val jsonArray = JSONArray(jsonResponse)
                             obj = jsonArray.getJSONObject(0)

                         } catch (e: JSONException) {
                             e.printStackTrace()
                         }
                         val road = Road(obj.getString("displayName"),obj.getString("statusSeverity"),obj.getString("statusSeverityDescription"),"no")
                         mResultData.postValue(road)
                    } else {
                         mResultData.postValue(Road(roadName,"","","The following road id is not recognised"))
                     }

             } catch (throwable: Throwable) {

             }
         }
    }

    fun getCurrentRoadStatus(): LiveData<Road>? {
        return mResultData
    }

    fun notNull(@Nullable toCheck: String?): Boolean {
        return toCheck != null && toCheck.trim { it <= ' ' }.isNotEmpty()
    }

    suspend fun getServerData( urlParam: String): String {
        var servicesAll = ""
        try {
            val url = URL(urlParam)
            Log.e("NetworkManager", url.toString())
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestMethod("GET")
            urlConnection.setConnectTimeout(5000) //set timeout to 5 seconds
            val `in`: InputStream = BufferedInputStream(urlConnection.getInputStream())
            servicesAll = inputStreamToString(`in`)
            val code: Int = urlConnection.getResponseCode()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return servicesAll
    }

    suspend fun inputStreamToString(`is`: InputStream): String {
        var rLine: String? = ""
        val answer = StringBuilder()
        val isr = InputStreamReader(`is`)
        val rd = BufferedReader(isr)
        try {
            while (rd.readLine().also { rLine = it } != null) {
                answer.append(rLine)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("NetworkManager", e.toString())
        }
        return answer.toString()
    }
}