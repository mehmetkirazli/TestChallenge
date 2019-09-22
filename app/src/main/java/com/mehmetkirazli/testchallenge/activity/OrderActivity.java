package com.mehmetkirazli.testchallenge.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mehmetkirazli.testchallenge.model.Order;
import com.mehmetkirazli.testchallenge.model.ProductDetail;
import com.mehmetkirazli.testchallenge.util.AppController;
import com.mehmetkirazli.testchallenge.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    Button btnMyOrders, btnCikis;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        btnMyOrders = findViewById(R.id.btnMyOrders);
        btnCikis = findViewById(R.id.btnCikis);

        btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("rememberMe", false);
                startActivity(new Intent(OrderActivity.this, LoginActivity.class));
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, "http://kariyertechchallenge.mockable.io/", new Response.Listener<String>() {
            @Override
            public void onResponse(String sonuc) { // sonuç başarılı dönerse onResponse çağrılır
                ArrayList<Order> orderList = jsonParser(sonuc); // parse metodu çağrıldı
                ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(OrderActivity.this, android.R.layout.simple_list_item_1, orderList);
//                listViewMarka.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) { // hata olursa volleyError nesnesinde hata sebebi yazar
                Toast.makeText(getApplicationContext(), "Veriler Okunurken Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request, "order");
    }

    ArrayList<Order> jsonParser(String okunanJson) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            JSONArray orderJsonArr = new JSONArray(okunanJson);
            for (int i = 0; i < orderJsonArr.length(); ++i) {
                orderList.add(new Order(orderJsonArr.getJSONObject(i).get("date").toString(), orderJsonArr.getJSONObject(i).get("month").toString(),
                        orderJsonArr.getJSONObject(i).get("marketName").toString(), orderJsonArr.getJSONObject(i).get("orderName").toString(), orderJsonArr.getJSONObject(i).get("productPrice").toString(),
                        orderJsonArr.getJSONObject(i).get("productState").toString(), new ProductDetail(orderJsonArr.getJSONObject(i).getJSONObject("productDetail").get("orderDetail").toString(), orderJsonArr.getJSONObject(i).getJSONObject("productDetail").get("summaryPrice").toString())));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }
}