package gesoft.gandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import gesoft.gapp.common.L;
import gesoft.gapp.common.T;

public class BluetoothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    BluetoothAdapter mBluetoothAdapter;
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<BluetoothDevice> mArrayList = new ArrayList<>();
    //BluetoothDevice mBluetoothDevice;
    ListView mListView;
    ListView mListViewHistory;
    ArrayAdapter<String> mArrayAdapterHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView = (ListView) findViewById(R.id.lv_history);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(this);

        mArrayAdapterHistory = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListViewHistory = (ListView) findViewById(R.id.lv_new);
        mListViewHistory.setAdapter(mArrayAdapterHistory);


        //通常我们使用该方法获得蓝牙的本地属性。
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 代表设备不支持蓝牙
        if (mBluetoothAdapter == null) {
            T.show(this, "设备不支持蓝牙");
        }else{
            //setCanScan();
        }
    }

    /**
     * 开启蓝牙
     */
    void openBluetooth() throws Exception{
        mBluetoothAdapter.enable();
    }

    /**
     * 注册用来接收扫描到的远程蓝牙设备的广播
     */
    void registerBluetoothReceiver() throws Exception{
        //unregisterReceiver(mReceiver);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 开始扫描
     * @param view
     */
    public void scanStart( View view ){
        try {
            openBluetooth();
            registerBluetoothReceiver();
            mArrayAdapter.clear();
            mArrayAdapter.notifyDataSetChanged();
            mBluetoothAdapter.startDiscovery();
            getDevices();
        } catch (Exception e) {
            L.e(e);
        }
    }

    /**
     * 获取已经配对过的设备
     * @throws Exception
     */
    void getDevices() throws Exception{
        mArrayAdapterHistory.clear();
        mArrayAdapterHistory.notifyDataSetChanged();
        Set<BluetoothDevice> devices  = mBluetoothAdapter.getBondedDevices();
        if( devices.size()>0 ){
            for (BluetoothDevice device : devices) {
                String deviceMsg = device.getName() + "\n" + device.getAddress();
                mArrayAdapterHistory.add( deviceMsg );
            }
            mArrayAdapterHistory.notifyDataSetChanged();
        }
    }

    /**
     * 取消扫描
     * 蓝牙扫描是非常耗费资源与时间的
     * 当我们扫描到需要操作的设备的时候
     * 我们需要停止扫描来获得更好的连接效率。
     * @param view
     */
    public void scanStop( View view ){
        mBluetoothAdapter.cancelDiscovery();
    }

    /**
     * 设置当前设备的蓝牙可见（可被搜索）
     */
    public void setCanScan(){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);//后面的参数是可被发现的时间，最大支持3600秒
        startActivity(discoverableIntent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //不要忘了在onDestroy注销广播哟
        unregisterReceiver(mReceiver);
    }

    /**
     * 蓝牙搜寻广播
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 当搜寻到设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获得远程设备信息
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceMsg = device.getName() + "\n" + device.getAddress();

                // 保存设备的mac地址与蓝牙名称
                if( mArrayAdapter.getPosition(deviceMsg)==-1 ){
                    mArrayAdapter.add( deviceMsg );
                    mArrayAdapter.notifyDataSetChanged();
                    mArrayList.add(device);
                }

            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            BluetoothDevice device = mArrayList.get(position);
            //检查是否处于未配对状态
            if ( device.getBondState() == BluetoothDevice.BOND_NONE) {
                T.show("开始配对");
                Method creMethod = BluetoothDevice.class.getMethod("createBond");
                creMethod.invoke( device );
            }
        } catch (Exception e) {
            Log.e("TAG", "开始配对");
        }
    }
}
