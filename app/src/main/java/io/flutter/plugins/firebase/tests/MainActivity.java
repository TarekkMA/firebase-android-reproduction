package io.flutter.plugins.firebase.tests;

import android.nfc.Tag;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private final String TAG = "firebase-auth-test";
    private Double readValue = 0.0;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("flutter-tests").document("testv").addSnapshotListener(
                (value, error) -> {
                    String sval = "";
                    if (value.contains("v")) {
                        readValue = 0.0;
                        sval = "0";
                    } else {
                        readValue = value.getDouble("v");
                        sval = readValue.toString();
                    }
                    binding.textval.setText(sval);
                }
        );

        binding.fab.setOnClickListener(view -> {
            increment(-1);
            FirebaseFirestore.getInstance().disableNetwork();
            increment(1);
            FirebaseFirestore.getInstance().enableNetwork();
        });
    }

    private void increment(int i) {
        Log.d("TEST", "increment " + i +" START");
        HashMap<String, Object> newObj = new HashMap<>();
        newObj.put("v", readValue + i);
        FirebaseFirestore
                .getInstance()
                .collection("flutter-tests")
                .document("testv")
                .update(newObj)
                .addOnSuccessListener(command -> {
                    Log.d("TEST", "increment " + i +" DONE");
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