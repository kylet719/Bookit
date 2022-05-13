package com.example.bookit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.bookit.Data.Book;
import com.example.bookit.Data.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int MY_PERMISSIONS_REQUEST_CAMERA = 0;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void populateLibrary(View v) {
        Book b1 = new Book ("Dune", "Frank Herbert" , 1, 256, "https://images-na.ssl-images-amazon.com/images/I/81ym3QUd3KL.jpg");
        Book b2 = new Book ("Neuromancer", "William Gibson" , 1, 234, "https://images-na.ssl-images-amazon.com/images/I/51OATljr5VL._SX331_BO1,204,203,200_.jpg");
        Book b3 = new Book ("A Father First", "Dwyane Wade" , 1, 52,"https://images-na.ssl-images-amazon.com/images/I/71Fe9+eilQL.jpg");
        Book b4 = new Book ("Fahrenheit 495", "Ray Bradburn", 250, 500, "https://images-na.ssl-images-amazon.com/images/I/71OFqSRFDgL.jpg" );
        Book b5 = new Book("To Kill A Mockingbird", "Harper Lee", 1, 250, "https://cdn.britannica.com/21/182021-050-666DB6B1/book-cover-To-Kill-a-Mockingbird-many-1961.jpg");
        Book b6 = new Book("Pride & Prejudice", "Jane Austin", 1, 250,"https://images-na.ssl-images-amazon.com/images/I/21164+rhPSL._SX331_BO1,204,203,200_.jpg");
        Book b7 = new Book("Crazy  Rich Asians", "Kevin Kwan", 1, 250, "https://images-na.ssl-images-amazon.com/images/I/71G9UC7ZwUL.jpg");
        Book b8 = new Book("Mona Lisa Overdrive","William Gibson", 1,250, "https://images-na.ssl-images-amazon.com/images/I/61AXAMwmRiL.jpg");
        Book b9 = new Book("Fear & Loathing in Las Vegas","Hunter S. Thompson", 1,250,"https://upload.wikimedia.org/wikipedia/en/7/7c/Fear_and_Loathing_in_Las_Vegas.jpg");
        Book b10 = new Book("Children of Dune","Frank Herbert",1,250,"https://images-na.ssl-images-amazon.com/images/I/91p+ea1rK3L.jpg");
        Book b11 = new Book("Dune Messiah","Frank Herbert",1,250,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTI7AywHmq1Xuoz3RAilq7LvUFfpm2mlvt7g&usqp=CAU");




        DBHelper db = new DBHelper(this);
        db.addOne(b1);
        db.addOne(b2);
        db.addOne(b3);
        db.addOne(b4);
        db.addOne(b5);
        db.addOne(b6);
        db.addOne(b7);
        db.addOne(b8);
        db.addOne(b9);
        db.addOne(b10);
        db.addOne(b11);

        Toast.makeText(this, "Added Books", Toast.LENGTH_SHORT).show();

    }
}