package com.dyc.learnandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.os.Handler;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivityWIF extends AppCompatActivity {
    public static final int NP_CELL_INFO_UPDATE = 1001;
    private PhoneInfoThread phoneInfoThread;
    public Handler mMainHandler;
    // for current
    public PhoneGeneralInfo phoneGeneralInfo;
    public CellGeneralInfo serverCellInfo;
    //for history
    private List<CellGeneralInfo> HistoryServerCellList;
    //    private CellnfoRecycleViewAdapter historyRecycleViewAdapter;
    private RecyclerView historyrecyclerView;
    TelephonyManager phoneManager;
    private MyPhoneStateListener myPhoneStateListener;

    void InitProcessThread() {
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == NP_CELL_INFO_UPDATE) {
                    Bundle bundle = msg.getData();
                    TextView tvTime = (TextView) findViewById(R.id.tvTimeleaps);
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    tvTime.setText(formatter.format(now));
//                    historyRecycleViewAdapter.notifyDataSetChanged();


                    TextView tvDeviceId = (TextView) findViewById(R.id.tvDeviceId);
                    tvDeviceId.setText("DeviceId:" + phoneGeneralInfo.deviceId);

                    TextView tvRatType = (TextView) findViewById(R.id.tvRatType);
                    tvRatType.setText("RatType:" + phoneGeneralInfo.ratType);

                    TextView tvMnc = (TextView) findViewById(R.id.tMnc);
                    tvMnc.setText("Mnc:" + phoneGeneralInfo.mnc);

                    TextView tvMcc = (TextView) findViewById(R.id.tvMcc);
                    tvMcc.setText("Mcc:" + phoneGeneralInfo.mcc);

                    TextView tvOperatorName = (TextView) findViewById(R.id.tvOperaterName);
                    tvOperatorName.setText("Operator:" + phoneGeneralInfo.operaterName);

                    TextView tvSdk = (TextView) findViewById(R.id.tvSdk);
                    tvSdk.setText("Sdk:" + phoneGeneralInfo.sdk);

                    TextView tvImsi = (TextView) findViewById(R.id.tvImsi);
                    tvImsi.setText("Imsi:" + phoneGeneralInfo.Imsi);

                    TextView tvSerialNum = (TextView) findViewById(R.id.tvSerialNum);
                    tvSerialNum.setText("SN:" + phoneGeneralInfo.serialNumber);

                    TextView tvModel = (TextView) findViewById(R.id.tvModel);
                    tvModel.setText("Model:" + phoneGeneralInfo.phoneModel);

                    TextView tvSoftwareVersion = (TextView) findViewById(R.id.tvSoftware);
                    tvSoftwareVersion.setText("Version:" + phoneGeneralInfo.deviceSoftwareVersion);

                    TextView tvAllCellInfo = (TextView) findViewById(R.id.tvStaticInfoLableHistory);
                    tvAllCellInfo.setText("History cells list(" + HistoryServerCellList.size() + ")");

                }
                super.handleMessage(msg);
            }
        };

        phoneInfoThread = new PhoneInfoThread(MainActivityWIF.this);
        phoneInfoThread.start();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serverCellInfo = new CellGeneralInfo();
        phoneGeneralInfo = new PhoneGeneralInfo();
        myPhoneStateListener = new MyPhoneStateListener();
        phoneManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        phoneManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        //
        HistoryServerCellList = new ArrayList<CellGeneralInfo>();
        historyrecyclerView = (RecyclerView) findViewById(R.id.historyrcv);
        LinearLayoutManager historylayoutManager = new LinearLayoutManager(this);
        historylayoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyrecyclerView.setLayoutManager(historylayoutManager);
//        historyRecycleViewAdapter  = new CellnfoRecycleViewAdapter(MainActivityWIF.this,HistoryServerCellList);
//        historyrecyclerView.setAdapter(historyRecycleViewAdapter);
//        historyrecyclerView.setItemAnimator(new DefaultItemAnimator());

        InitProcessThread();

    }

    public void updateServerCellView() {
        TextView tvCellType = (TextView) findViewById(R.id.tvCellType);
        tvCellType.setText("Rat:" + serverCellInfo.type);

        TextView tvTac = (TextView) findViewById(R.id.tvTac);
        tvTac.setText("Tac:" + serverCellInfo.tac);

        TextView tvCellId = (TextView) findViewById(R.id.tvCellId);
        tvCellId.setText("Ci:" + serverCellInfo.CId);

        TextView tvPCI = (TextView) findViewById(R.id.tvPCI);
        tvPCI.setText("Pci:" + serverCellInfo.pci);

        TextView tvRsrp = (TextView) findViewById(R.id.tvRsrp);
        tvRsrp.setText("Rsrp:" + serverCellInfo.rsrp);

        TextView tvRsrq = (TextView) findViewById(R.id.tvRsrq);
        tvRsrq.setText("Rsrp:" + serverCellInfo.rsrq);

        TextView tvSINR = (TextView) findViewById(R.id.tvSINR);
        tvSINR.setText("Sinr:" + serverCellInfo.sinr);

        TextView tvCqi = (TextView) findViewById(R.id.tvCqi);
        tvCqi.setText("cqi:" + serverCellInfo.cqi);

        TextView tvGetCellType = (TextView) findViewById(R.id.tvGetCellType);
        tvGetCellType.setText("type:" + serverCellInfo.getInfoType);
    }

    class PhoneGeneralInfo {
        public String serialNumber;
        public String operaterName;
        public String operaterId;
        public String deviceId;
        public String deviceSoftwareVersion;
        public String Imsi;
        public String Imei;
        public int mnc;
        public int mcc;
        public int ratType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
        public int phoneDatastate;
        public String phoneModel;
        public int sdk;
    }

    class CellGeneralInfo implements Cloneable {
        public int type;
        public int CId;
        public int lac;
        public int tac;
        public int psc;
        public int pci;
        public int RatType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
        public int rsrp;
        public int rsrq;
        public int sinr;
        public int rssi;
        public int cqi;
        public int asulevel;
        public int getInfoType;
        public String time;

        @Override

        public Object clone() {
            CellGeneralInfo cellinfo = null;
            try {
                cellinfo = (CellGeneralInfo) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return cellinfo;
        }

        @Override
        public String toString() {
            return "CellGeneralInfo{" +
                    "type=" + type +
                    ", CId=" + CId +
                    ", lac=" + lac +
                    ", tac=" + tac +
                    ", psc=" + psc +
                    ", pci=" + pci +
                    ", RatType=" + RatType +
                    ", rsrp=" + rsrp +
                    ", rsrq=" + rsrq +
                    ", sinr=" + sinr +
                    ", rssi=" + rssi +
                    ", cqi=" + cqi +
                    ", asulevel=" + asulevel +
                    ", getInfoType=" + getInfoType +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            getPhoneGeneralInfo();
            getServerCellInfo();
            if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_LTE) {
                try {
                    serverCellInfo.rssi = (Integer) signalStrength.getClass().getMethod("getLteSignalStrength").invoke(signalStrength);
                    serverCellInfo.rsrp = (Integer) signalStrength.getClass().getMethod("getLteRsrp").invoke(signalStrength);
                    serverCellInfo.rsrq = (Integer) signalStrength.getClass().getMethod("getLteRsrq").invoke(signalStrength);
                    serverCellInfo.sinr = (Integer) signalStrength.getClass().getMethod("getLteRssnr").invoke(signalStrength);
                    serverCellInfo.cqi = (Integer) signalStrength.getClass().getMethod("getLteCqi").invoke(signalStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_GSM) {
                try {
                    serverCellInfo.rssi = signalStrength.getGsmSignalStrength();
                    serverCellInfo.rsrp = (Integer) signalStrength.getClass().getMethod("getGsmDbm").invoke(signalStrength);
                    serverCellInfo.asulevel = (Integer) signalStrength.getClass().getMethod("getAsuLevel").invoke(signalStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else if (phoneGeneralInfo.ratType == TelephonyManager.NETWORK_TYPE_TD_SCDMA) {
                try {
                    serverCellInfo.rssi = (Integer) signalStrength.getClass().getMethod("getTdScdmaLevel").invoke(signalStrength);
                    serverCellInfo.rsrp = (Integer) signalStrength.getClass().getMethod("getTdScdmaDbm").invoke(signalStrength);
                    serverCellInfo.asulevel = (Integer) signalStrength.getClass().getMethod("getAsuLevel").invoke(signalStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            serverCellInfo.time = formatter.format(now);
            updateHistoryCellList(serverCellInfo);
            updateServerCellView();
        }

        @SuppressLint("HardwareIds")
        public void getPhoneGeneralInfo() {
            phoneGeneralInfo.operaterName = phoneManager.getNetworkOperatorName();
            phoneGeneralInfo.operaterId = phoneManager.getNetworkOperator();
            phoneGeneralInfo.mnc = Integer.parseInt(phoneGeneralInfo.operaterId.substring(0, 3));
            phoneGeneralInfo.mcc = Integer.parseInt(phoneGeneralInfo.operaterId.substring(3));
            phoneGeneralInfo.phoneDatastate = phoneManager.getDataState();
//            phoneGeneralInfo.deviceId = phoneManager.getDeviceId();
//            phoneGeneralInfo.Imei = phoneManager.getSimSerialNumber();
//            phoneGeneralInfo.Imsi = phoneManager.getSubscriberId();
//            phoneGeneralInfo.serialNumber = phoneManager.getSimSerialNumber();
            phoneGeneralInfo.deviceSoftwareVersion = android.os.Build.VERSION.RELEASE;
            phoneGeneralInfo.phoneModel = android.os.Build.MODEL;

            if (ActivityCompat.checkSelfPermission(MainActivityWIF.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            phoneGeneralInfo.ratType = phoneManager.getNetworkType();
            phoneGeneralInfo.sdk = android.os.Build.VERSION.SDK_INT;
        }

        public void getServerCellInfo() {
            try {
                List<CellInfo> allCellinfo;
                if (ActivityCompat.checkSelfPermission(MainActivityWIF.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                allCellinfo = phoneManager.getAllCellInfo();
                if (allCellinfo != null) {
                    CellInfo cellInfo = allCellinfo.get(0);
                    serverCellInfo.getInfoType = 1;
                    if (cellInfo instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        serverCellInfo.CId = cellInfoGsm.getCellIdentity().getCid();
                        serverCellInfo.rsrp = cellInfoGsm.getCellSignalStrength().getDbm();
                        serverCellInfo.asulevel = cellInfoGsm.getCellSignalStrength().getAsuLevel();
                        serverCellInfo.lac = cellInfoGsm.getCellIdentity().getLac();
                        serverCellInfo.RatType = TelephonyManager.NETWORK_TYPE_GSM;
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                        serverCellInfo.CId = cellInfoWcdma.getCellIdentity().getCid();
                        serverCellInfo.psc = cellInfoWcdma.getCellIdentity().getPsc();
                        serverCellInfo.lac = cellInfoWcdma.getCellIdentity().getLac();
                        serverCellInfo.rsrp = cellInfoWcdma.getCellSignalStrength().getDbm();
                        serverCellInfo.asulevel = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
                        serverCellInfo.RatType = TelephonyManager.NETWORK_TYPE_UMTS;
                    } else if (cellInfo instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        serverCellInfo.CId = cellInfoLte.getCellIdentity().getCi();
                        serverCellInfo.pci = cellInfoLte.getCellIdentity().getPci();
                        serverCellInfo.tac = cellInfoLte.getCellIdentity().getTac();
                        serverCellInfo.rsrp = cellInfoLte.getCellSignalStrength().getDbm();
                        serverCellInfo.asulevel = cellInfoLte.getCellSignalStrength().getAsuLevel();
                        serverCellInfo.RatType = TelephonyManager.NETWORK_TYPE_LTE;
                    }
                } else
                //for older devices
                {
                    getServerCellInfoOnOlderDevices();
                }
            } catch (Exception e) {
                getServerCellInfoOnOlderDevices();
            }

        }

        void getServerCellInfoOnOlderDevices() {
            if (ActivityCompat.checkSelfPermission(MainActivityWIF.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            GsmCellLocation location = (GsmCellLocation) phoneManager.getCellLocation();
            serverCellInfo.getInfoType = 0;
            serverCellInfo.CId = location.getCid();
            serverCellInfo.tac = location.getLac();
            serverCellInfo.psc = location.getPsc();
            serverCellInfo.type = phoneGeneralInfo.ratType;
        }
 
        void updateHistoryCellList(CellGeneralInfo serverinfo)
        {
            Log.d("CellGeneralInfo",serverinfo.toString());
            CellGeneralInfo newcellInfo = (CellGeneralInfo)serverinfo.clone();
            HistoryServerCellList.add(newcellInfo);
        }
    }
     class PhoneInfoThread extends  Thread
    {
        private Context context;
        public int timecount;
        public PhoneInfoThread(Context context)
        {
            this.context = context;
            timecount = 0;
        }
 
        public void run()
        {
           while (true) {
                try {
                    timecount++;
                    Message message = new Message();
                    message.what = NP_CELL_INFO_UPDATE;
                    Bundle bundle = new Bundle();
                    bundle.putString("UPDATE", "UPDATE_TIME");
                    message.setData(bundle);
                    mMainHandler.sendMessage(message);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
 