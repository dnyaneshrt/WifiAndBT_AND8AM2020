package cubex.mahesh.wifiandbt_and8am2020

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var status = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        if(status == PackageManager.PERMISSION_GRANTED)
        {
            getWifiBtConnectivityInfo()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            getWifiBtConnectivityInfo()
        }
    }

    fun getWifiBtConnectivityInfo()
    {
        var wManager :WifiManager = applicationContext.
            getSystemService(Context.WIFI_SERVICE) as WifiManager
        var wstate = wManager.wifiState
        s_wifi.isEnabled = wstate == WifiManager.WIFI_STATE_ENABLED ||
                wstate == WifiManager.WIFI_STATE_ENABLING
        s_wifi.setOnCheckedChangeListener { buttonView, isChecked ->
            wManager.isWifiEnabled = isChecked
        }
        getwifi.setOnClickListener {
          var list:MutableList<ScanResult> =  wManager.scanResults
          var listAsString = mutableListOf<String>()
            for(sresult in  list){
                listAsString.add(sresult.SSID +"\n"+ sresult.frequency)
            }
            var myadapter = ArrayAdapter<String>(
   this@MainActivity,
      android.R.layout.simple_list_item_multiple_choice,listAsString)
            lview.adapter = myadapter
        }

        pairedwifi.setOnClickListener {
            var list:MutableList<WifiConfiguration> =  wManager.configuredNetworks
            var listAsString = mutableListOf<String>()
            for(sresult in  list){
                var status = ""
                if(sresult.status == 0){
                    status = "Connected"
                }else if(sresult.status == 1){
                    status = "Network available"
                }else if(sresult.status == 2){
                    status = "Network not-available"
                }
                listAsString.add(sresult.SSID +"\n"+
                        status)
            }
            var myadapter = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_list_item_multiple_choice,listAsString)
            lview.adapter = myadapter
        }
        var bAdapter:BluetoothAdapter
                = BluetoothAdapter.getDefaultAdapter()
        s_bt.isEnabled = bAdapter.isEnabled
        s_bt.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                bAdapter.enable()
            }else{
                bAdapter.disable()
            }
        }
        getbt.setOnClickListener {

        }
    }
}
