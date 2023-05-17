package com.example.mylap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Order> listOrder = new ArrayList<>();
    private EditText soLuongSachInput;
    private CheckBox khachHangVipCheckbox;
    private EditText tenKhachHangInput;
    private TextView tongTienTextView;
    private TextView tongSoKhTextView;
    private TextView tongSoKhVipTextView;
    private TextView tongDoanhThuTextView;

    private void ClearText() {
        tenKhachHangInput.setText("");
        soLuongSachInput.setText("");
        khachHangVipCheckbox.setChecked(false);
        tenKhachHangInput.requestFocus();
        tongTienTextView.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soLuongSachInput = (EditText) findViewById(R.id.so_luong_sach_input);
        khachHangVipCheckbox = (CheckBox) findViewById(R.id.khach_hang_vip_checkbox);
        tongTienTextView = (TextView) findViewById(R.id.tong_tien_text_view);
        tenKhachHangInput = findViewById(R.id.ten_khach_hang_input);
        tongSoKhTextView = findViewById(R.id.tong_so_kh_text_view);
        tongSoKhVipTextView = findViewById(R.id.tong_so_kh_vip_text_view);
        tongDoanhThuTextView = findViewById(R.id.tong_doanh_thu_text_view);
        Button tinhTongTienButton = (Button) findViewById(R.id.tinh_tong_tien_button);
        Button tiepButton = findViewById(R.id.tiep_button);
        Button thongKeButton = findViewById(R.id.thong_ke_button);
        Button thoatButton = findViewById(R.id.thoat_button);
        tinhTongTienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongSach = Integer.parseInt(soLuongSachInput.getText().toString());
                int donGia = 20000;
                int tongTien = soLuongSach * donGia;

                if (khachHangVipCheckbox.isChecked()) {
                    tongTien -= tongTien * 0.1;
                }
                tongTienTextView.setText("" +tongTien);
//                Toast.makeText(getApplicationContext(), "Tổng tiền là: " + tongTien, Toast.LENGTH_SHORT).show();
            }
        });

        tiepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu thông tin hóa đơn vào biến listOrder
                String tenKhachHang = tenKhachHangInput.getText().toString();
                int soLuongSach = Integer.parseInt(soLuongSachInput.getText().toString());
                boolean khachHangVip = khachHangVipCheckbox.isChecked();
                int tongTien = Integer.parseInt(tongTienTextView.getText().toString());
                Order order = new Order(tenKhachHang, soLuongSach, khachHangVip, tongTien);
                listOrder.add(order);

                // Xóa trắng các thông tin đã nhập và focus vào EditText tên khách hàng
                ClearText();
            }
        });

        thongKeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tính tổng số KH, tổng số KH VIP và tổng doanh thu từ biến listOrder
                int tongSoKh = listOrder.size();
                int tongSoKhVip = 0;
                int tongDoanhThu = 0;
                for (Order order : listOrder) {
                    if (order.isVip()) {
                        tongSoKhVip++;
                    }
                    tongDoanhThu += order.getTotalBill();
                }

                // Hiển thị thông tin tổng số KH, tổng số KH VIP và tổng doanh thu
                tongSoKhTextView.setText("" + tongSoKh);
                tongSoKhVipTextView.setText("" + tongSoKhVip);
                tongDoanhThuTextView.setText("" + tongDoanhThu);

                ClearText();
            }
        });

        thoatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có muốn thoát không?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Thoát khỏi ứng dụng
                                finish();
//                                System.exit(0);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Đóng dialog
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}