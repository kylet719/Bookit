package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private final String urlSite = "https://www.bookfinder.com/search/?author=&title=&lang=en&isbn=";
    private final String urlSite2 = "&new_used=*&destination=ca&currency=CAD&mode=basic&st=sr&ac=qr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanneview);
        mCodeScanner = new CodeScanner(this, scannerView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        mCodeScanner.setDecodeCallback(result -> this.runOnUiThread(() -> {
            TextView holder = findViewById(R.id.scanResults);
            String ibsn = result.getText();
            String[] bookDetails = getInfo(ibsn);
            Intent i = new Intent(this, AddNew.class);
            i.putExtra("data",bookDetails);
            startActivity(i);
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    private String[] getInfo(String i) {
        String[] data = {"title", "author", "url"};
        String bookPage = urlSite + i + urlSite2;

        try {
            Document document = Jsoup.connect(bookPage).get();

            //Title
            Elements title = document.select("span[id^=describe-isbn-title]");
            data[0] = title.text();

            //Author
            Elements author = document.select("span[itemprop = author]");
            data[1] = author.text();

            // Get cover
            data[2] = document.select("img[id^=coverImage]").attr("src");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}