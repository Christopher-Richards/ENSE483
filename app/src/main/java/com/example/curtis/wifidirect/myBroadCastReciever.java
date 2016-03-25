package com.example.curtis.wifidirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.*;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;
/**
 * Created by curtis on 2016-03-21.
 */
public class myBroadCastReciever extends BroadcastReceiver {
    private WifiP2pManager mManager;
    PeerListListener myPeerListListener;
     Channel mChannel;
    private MainActivity mActivity;

    public myBroadCastReciever(WifiP2pManager manager, Channel channel,
                                      MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("myBroadCastReciever", "onRecieveTriggered");
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mActivity.setIsWifiP2pEnabled(true);
                Log.d("myBroadcastReciever", "WifiP2PEnabled");
            } else {
                mActivity.setIsWifiP2pEnabled(false);
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed!  We should probably do something about
            // that.
            Log.d("myBroadCastReciever", "Peers Changed");
            if (mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
            }


            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

                // Connection state changed!  We should probably do something about
                // that.

            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*DeviceListFragment fragment = (DeviceListFragment) mActivity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
            */
            }



        }

    public void setIsWifiP2pEnabled(boolean Isit)
    {

    }
    public void getFragmentManager()
    {

    }
}
