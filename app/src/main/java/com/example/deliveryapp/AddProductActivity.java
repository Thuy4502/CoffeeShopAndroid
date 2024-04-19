package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private Button btnAddProduct, btnAddImg, btnEditImg;
    private ImageView ivGallery;
    private ImageView ivBack;
    public final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_product);
        setControl();
        setEvent();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == GALLERY_REQ_CODE) {
                ivGallery.setImageURI(data.getData());
            }
        }
    }

    protected void setControl() {
        btnAddProduct = findViewById(R.id.btnSave);
        btnAddImg = findViewById(R.id.btnEditImg);
        ivBack = findViewById(R.id.ivBack);
        btnEditImg = findViewById(R.id.btnEditImg);
        ivGallery = findViewById(R.id.ivGallery);
    }

    protected void setEvent() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddProduct.setAllCaps(false);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddProductActivity.this, SucessfullyAdd.class);
//                startActivity(intent);
            }
        });

        btnAddImg.setAllCaps(false);

        btnEditImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

    }



}
