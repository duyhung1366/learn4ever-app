package com.example.mylap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.content.Intent;

import android.util.Log;


public class ListActivity extends AppCompatActivity {
    ListView listView;
    EditText maNv;
    EditText tenNv;
    //    RadioButton chinhThuc;
//    RadioButton Thoivu;
    Button Themnv;
    Button GotoMain;
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        maNv = findViewById(R.id.ma_nv);
        tenNv = findViewById(R.id.ten_nv);
//        chinhThuc = findViewById(R.id.nv_chinhthuc);
//        Thoivu = findViewById(R.id.nv_thoivu);
        Themnv = findViewById(R.id.nhapnhanvien);
        GotoMain= findViewById(R.id.gotoActiviMain);
        spinner = findViewById(R.id.option_spinner);
        ArrayList<String> datasSamSung = new ArrayList<String>();
        ArrayList<String> datasIphone = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        ArrayList<String> optionsList = new ArrayList<String>();
        optionsList.add("SamSung");
        optionsList.add("Iphone");
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị tùy chọn được chọn
                String selectedOption = (String) parent.getSelectedItem();
                adapter.clear();
                if (selectedOption == "SamSung") {
                    adapter.addAll(datasSamSung);
                } else {
                    adapter.addAll(datasIphone);
                }
                // Xử lý dữ liệu
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có tùy chọn nào được chọn
            }
        });

//        list view
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Themnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                if (spinner.getSelectedItem() == "SamSung") {
                    datasSamSung.add(maNv.getText() + "-" + tenNv.getText());
                    adapter.addAll(datasSamSung);
                } else {
                    datasIphone.add(maNv.getText() + "-" + tenNv.getText());
                    adapter.addAll(datasIphone);
                }
                adapter.notifyDataSetChanged();
            }
        });

        GotoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển màn hình khi nhấn vào Button
                Intent intent = new Intent(ListActivity.this, ListCategory.class);
                startActivity(intent);
            }
        });
    }
}
