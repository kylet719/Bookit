package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
    private String[] scannedBookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanneview);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> this.runOnUiThread(() -> {
            TextView holder = findViewById(R.id.scanResults);
            String ibsn = result.getText();

            // New thread for network request
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    getInfo(ibsn);
                }
            });
            thread.start();
            try {
                Toast.makeText(this, "Pulling Book Info", Toast.LENGTH_SHORT).show();
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(this, AddNew.class);
            i.putExtra("data", scannedBookData);
            startActivity(i);
        }));

        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    /**
     * Method for new thread to conduct JSoup API call and scrape for book info
     *
     * @param i - the ISBN book number scanned by Scanner
     * @return - nothing
     */
    private void getInfo(String i) {
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
        scannedBookData = data;
    }
}