package com.mehmetkirazli.testchallenge.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mehmetkirazli.testchallenge.R;
import com.mehmetkirazli.testchallenge.model.Order;
import com.mehmetkirazli.testchallenge.model.ProductDetail;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<Order> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Order> orders;
    ProductDetail model;

    public CustomListViewAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
        this.context = context;
        this.orders = orders;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_item, null);
            holder = new ViewHolder();
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvMonth = convertView.findViewById(R.id.tvMonth);
            holder.tvMarketName = convertView.findViewById(R.id.tvMarketName);
            holder.tvOrderName = convertView.findViewById(R.id.tvOrderName);
            holder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.tvProductState = convertView.findViewById(R.id.tvProductState);
            holder.tvOrderDetail = convertView.findViewById(R.id.tvOrderDetail);
            holder.tvSummaryPrice = convertView.findViewById(R.id.tvSummaryPrice);
            holder.btnState = convertView.findViewById(R.id.btnState);
            holder.layDetail = convertView.findViewById(R.id.layDetail);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        final Order order = orders.get(position);
        if (order != null) {
            holder.tvDate.setText(order.getDate());
            holder.tvMonth.setText(new DateFormatSymbols().getMonths()[Integer.valueOf(order.getMonth()) - 1]);
            holder.tvMarketName.setText(order.getMarketName());
            holder.tvOrderName.setText(order.getOrderName());
            holder.tvProductPrice.setText(order.getProductPrice() + " TL");
            holder.tvProductState.setText(order.getProductState());
            holder.tvOrderDetail.setText(order.getProductDetail().getOrderDetail());
            holder.tvSummaryPrice.setText(order.getProductDetail().getSummaryPrice() + " TL");

            if (order.getProductState().equals("Hazırlanıyor")) { // sipariş durumuna göre, sipariş durum metni ve ikonu renklendirildi
                holder.btnState.setBackgroundResource(R.drawable.order_state1_background);
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.colorOrange));
            } else if (order.getProductState().equals("Yolda")) {
                holder.btnState.setBackgroundResource(R.drawable.order_state2_background);
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.colorGreen));
            } else if (order.getProductState().equals("Onay Bekliyor")) {
                holder.btnState.setBackgroundResource(R.drawable.order_state3_background);
                holder.tvProductState.setTextColor(context.getResources().getColor(R.color.colorRed));
            }

            model = orders.get(position).productDetail;
            if (model.isVisible()) { // liste yenilendikçe item durumuna göre tekrar visible değeri set ediliyor
                holder.layDetail.setVisibility(View.VISIBLE);
            } else
                holder.layDetail.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // tıklanan item in visible değeri değişiyor
                model = orders.get(position).productDetail;
                if (model.isVisible()) {
                    model.setVisible(false);
                } else
                    model.setVisible(true);
                orders.get(position).setProductDetail(model); // listede de değişiklik yapılıyor
                notifyDataSetChanged(); // liste yenileniyor
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView tvDate, tvMonth, tvMarketName, tvOrderName, tvProductPrice, tvProductState, tvOrderDetail, tvSummaryPrice;
        Button btnState;
        LinearLayout layDetail;
    }
}