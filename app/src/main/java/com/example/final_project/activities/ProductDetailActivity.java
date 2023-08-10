package com.example.final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityProductDetailBinding;
import com.example.final_project.model.Product;
import com.example.final_project.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {


    ActivityProductDetailBinding binding;
    Product currentProduct;
    TextView productName, productPrice, productCategory, productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productName = binding.productName;
        productPrice = binding.productPrice;
        productCategory = binding.productCategory;
        productDescription = binding.productDescription;

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        String category = getIntent().getStringExtra("categoryName");
        String description = getIntent().getStringExtra("description");
        int id = getIntent().getIntExtra("id", 0);
        double price = getIntent().getDoubleExtra("price", 0);
        int categoryId = getIntent().getIntExtra("categoryId", 0);
        String status = getIntent().getStringExtra("status");
        double discount = getIntent().getDoubleExtra("discount",0);
        int stock = getIntent().getIntExtra("stock",0);

        String priceofProduct = String.valueOf(price);

        productName.setText(name);
        productPrice.setText("Cad "+priceofProduct);
        productCategory.setText("category: "+category);
        productDescription.setText(description);

        currentProduct = new Product(name, image, price, description, id, categoryId, category, status, discount, stock);

        Glide.with(this)
                .load(image)
                .into(binding.productImage);

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Cart cart = TinyCartHelper.getCart();

        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct, 1);
                binding.addToCartBtn.setEnabled(false);
                binding.addToCartBtn.setText("Added in cart");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}