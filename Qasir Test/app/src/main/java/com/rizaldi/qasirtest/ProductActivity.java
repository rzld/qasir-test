package com.rizaldi.qasirtest;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar2);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView productImage = (ImageView) findViewById(R.id.productImage);
        TextView productName = (TextView) findViewById(R.id.productName2);
        TextView productPrice = (TextView) findViewById(R.id.productPrice);
        TextView description = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();
        String productImageS = intent.getStringExtra(CustomAdapter.KEY_IMAGE);
        final String productNameS = intent.getStringExtra(CustomAdapter.KEY_NAME);
        final Integer productPriceS = intent.getIntExtra(CustomAdapter.KEY_PRICE, 0);
        final String descriptionS = intent.getStringExtra(CustomAdapter.KEY_DESCRIPTION);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Glide.with(this).load(productImageS).into(productImage);
        productName.setText(productNameS);
        productPrice.setText(formatRupiah.format((float)productPriceS));
        description.setText(Html.fromHtml(descriptionS, Html.FROM_HTML_MODE_COMPACT));
        toolbar.setTitle(productNameS);
    }
}
