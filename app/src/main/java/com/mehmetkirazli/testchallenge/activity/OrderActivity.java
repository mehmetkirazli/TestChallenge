package com.mehmetkirazli.testchallenge.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mehmetkirazli.testchallenge.BuildConfig;
import com.mehmetkirazli.testchallenge.adapter.CustomListViewAdapter;
import com.mehmetkirazli.testchallenge.model.Order;
import com.mehmetkirazli.testchallenge.model.ProductDetail;
import com.mehmetkirazli.testchallenge.util.AppController;
import com.mehmetkirazli.testchallenge.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    Button btnMyOrders, btnExit;
    SharedPreferences.Editor editor;
    ListView lvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        btnMyOrders = findViewById(R.id.btnMyOrders);
        btnExit = findViewById(R.id.btnExit);
        lvOrders = findViewById(R.id.lvOrders);

        editor = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE).edit();

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // çıkış yapmak istediğinde uyarı gösterilir
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setTitle(getResources().getString(R.string.txt_warning));
                builder.setMessage(getResources().getString(R.string.txt_are_you_sure));
                builder.setPositiveButton(getResources().getString(R.string.txt_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putBoolean("rememberMe", false); // manuel çıkış yapılırsa beni hatırla aktif olmayacak
                        editor.apply();
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.txt_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        // volley kütüphanesiyle servise istek yapıldı. alternatif olarak retrofit de kıllanılabilirdi
        StringRequest request = new StringRequest(Request.Method.GET, "http://kariyertechchallenge.mockable.io/", new Response.Listener<String>() {
            @Override
            public void onResponse(String sonuc) { // sonuç başarılı dönerse onResponse çağrılır
                ArrayList<Order> orderList = jsonParser(sonuc); // dönen veri parse edildi. 3.parti kütüphane kullanmadan basitçe parse işlemi yapıldı
                CustomListViewAdapter listViewAdapter = new CustomListViewAdapter(OrderActivity.this, orderList);
                lvOrders.setAdapter(listViewAdapter); // liste adaptöre set edildi
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { // hata olursa uyarı verilir
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_error), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request, "order"); // volley isteği kuyruğa eklendi
    }

    ArrayList<Order> jsonParser(String responseJson) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            JSONArray orderJsonArr = new JSONArray(responseJson);
            for (int i = 0; i < orderJsonArr.length(); ++i) { // tüm tag'ler parse edilerek model sınıfı ile birlikte listeye eklendi
                orderList.add(new Order(orderJsonArr.getJSONObject(i).get("date").toString(), orderJsonArr.getJSONObject(i).get("month").toString(),
                        orderJsonArr.getJSONObject(i).get("marketName").toString(), orderJsonArr.getJSONObject(i).get("orderName").toString(), orderJsonArr.getJSONObject(i).get("productPrice").toString(),
                        orderJsonArr.getJSONObject(i).get("productState").toString(), new ProductDetail(orderJsonArr.getJSONObject(i).getJSONObject("productDetail").get("orderDetail").toString(), orderJsonArr.getJSONObject(i).getJSONObject("productDetail").get("summaryPrice").toString(), false)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}