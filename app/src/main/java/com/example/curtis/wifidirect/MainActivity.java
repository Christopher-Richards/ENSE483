    package com.example.curtis.wifidirect;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager;
import java.util.ArrayList;
import java.util.List;


    public class MainActivity extends Activity {

    static string[] device_names = new string [] {};//to hold discovered device names
    static boolean connections_allowed;

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager mManager;
    Channel mChannel;
    myBroadCastReciever receiver = new myBroadCastReciever( mManager,mChannel, this);
    PeerListListener mPeerListListener;
    private List<WifiP2pDevice> Peers = new ArrayList<WifiP2pDevice>();


    Button connection_status_button = (Button) findViewById(R.id.B_connection_status);
        public void changeConnectionAvailability(){
            if(connections_allowed) {
                connection_status_button.setBackgroundColor(0xc3624);
                connections_allowed=false;
            }
            else {
                connection_status_button.setBackgroundColor(0x6aa121);
                connections_allowed=true;
            }
        }

     Button search_for_devices_button = (Button) findViewById(R.id.B_search_for_devices);
        public void searchForDevices(){

            //search for devices, populate list

        }








    protected void onCreate(Bundle savedInstanceState) {
        connections_allowed = false;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","You made a log");
        // My code
        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            Log.d("MainActivity","discovered Peers");
            }

            @Override
            public void onFailure(int reasonCode) {
            Log.d("MainActivity", "Failed to discover peers"+ " " + reasonCode);
            }});

        mPeerListListener = new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {
            Peers.clear();
                Peers.addAll(peerList.getDeviceList());
                if(Peers.size()==0)
                {
                    Log.d("wifi" ,"No devices found");
                    return;
                }
                WifiP2pDevice device;
                device = Peers.get(0);
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress= device.deviceAddress;
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("MainActivity", "You connected!");
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.d("MainActivity","You didnt connect because"+ " "+ reason);
                    }
                });

            }


        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled)
    {

    }


}
