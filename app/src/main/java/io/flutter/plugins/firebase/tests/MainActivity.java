package io.flutter.plugins.firebase.tests;

import android.nfc.Tag;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.flutter.plugins.firebase.tests.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private final String TAG = "firebase-auth-test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        io.flutter.plugins.firebase.tests.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        FirebaseApp.initializeApp(this);
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> {
            FirebaseApp app = FirebaseApp.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance(app);

            auth.setLanguageCode("en-US");

            auth.createUserWithEmailAndPassword("test-email@testemail.com", "qwerty123").addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User created successfully");

                } else {
                    Log.d(TAG, "User creation failed");
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}