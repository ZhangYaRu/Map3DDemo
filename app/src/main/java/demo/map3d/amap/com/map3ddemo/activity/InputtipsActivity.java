package demo.map3d.amap.com.map3ddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import demo.map3d.amap.com.map3ddemo.R;

/**
 * 输入提示功能实现
 */
public class InputtipsActivity extends Activity implements TextWatcher, InputtipsListener, AdapterView.OnItemClickListener {

    private String city = "北京";
    private AutoCompleteTextView mKeywordText;
    private ListView minputlist;
    private List<Tip> tipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputtip);
        minputlist = (ListView) findViewById(R.id.inputlist);
        mKeywordText = (AutoCompleteTextView) findViewById(R.id.input_edittext);
        mKeywordText.addTextChangedListener(this);

        minputlist.setOnItemClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, city);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(InputtipsActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();


    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    /**
     * 输入提示结果的回调
     *
     * @param tipList
     * @param rCode
     */
    @Override
    public void onGetInputtips(final List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            ArrayList<HashMap<String, String>> listString = new ArrayList<>();
            if (tipList != null) {
                int size = tipList.size();
                this.tipList = tipList;
                for (int i = 0; i < size; i++) {
                    Tip tip = tipList.get(i);
                    if (tip != null) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", tipList.get(i).getName());
                        map.put("address", tipList.get(i).getDistrict());
                        listString.add(map);
                    }
                }
                SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
                        new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

                minputlist.setAdapter(aAdapter);
                aAdapter.notifyDataSetChanged();
            }

        } else {
//            Logg(this.getApplicationContext(), rCode);
            Log.e("InputtipsActivity", String.valueOf(rCode));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (tipList != null) {
            Tip tip = tipList.get(position);
            Intent intent = new Intent();
            intent.putExtra("result", tip);
            setResult(3, intent);
            finish();
        }
    }
}
