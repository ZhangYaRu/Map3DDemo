package demo.map3d.amap.com.map3ddemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.help.Tip;

import java.io.Serializable;
import java.util.HashMap;

import demo.map3d.amap.com.map3ddemo.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvStart;
    private TextView tvEnd;
    private TextView tvChange;
    private Intent intent;
    private Tip endTip;
    private Tip startTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStart = findViewById(R.id.tv_start);
        tvEnd = findViewById(R.id.tv_end);
        tvChange = findViewById(R.id.tv_change);
        intent = new Intent(this, InputtipsActivity.class);
    }

    public void inputStart(View view) {
        startActivityForResult(intent, 1);
    }

    public void inputEnd(View view) {
        startActivityForResult(intent, 2);
    }

    boolean canExchange = true;

    public void exchange(View view) {
        if (canExchange) {
            canExchange = false;
            String temp = "";
            String start = tvStart.getText().toString();
            String end = tvEnd.getText().toString();
            temp = start;
            tvStart.setText(end);
            tvEnd.setText(temp);
            Tip t = startTip;
            startTip = endTip;
            endTip = t;
            canExchange = true;
        }
    }

    public void search(View view) {
        Intent intent = new Intent(this, RouteActivity.class);
        intent.putExtra("startTip", startTip);
        intent.putExtra("endTip", endTip);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Tip result = data.getParcelableExtra("result");
        String address = "";
        switch (requestCode) {
            case 1:
//                StringBuffer sb = new StringBuffer();
//                sb.append(result.getAdcode() + "\n");//110105
//                sb.append(result.getAddress() + "\n");//太阳宫中路12号
//                sb.append(result.getDistrict() + "\n");//北京市朝阳区
//                sb.append(result.getName() + "\n");//凯德MALL(太阳宫店)
//                sb.append(result.getPoiID() + "\n");// B000A8WAWL
//                sb.append(result.getPoint() + "\n");//纬度/经度
//                sb.append(result.getTypeCode() + "\n");// 060101
                address = result.getName() + "\t" + result.getDistrict() + result.getAddress();
                tvStart.setText(address);
                startTip = result;
                break;
            case 2:
                address = result.getName() + "\t" + result.getDistrict() + result.getAddress();
                tvEnd.setText(address);
                endTip = result;
                break;
        }
    }


}
