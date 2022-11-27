package in.narenbairagi.contects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import in.narenbairagi.contects.data.MyDbHendler;

public class MainActivity extends AppCompatActivity {

    MaterialButton saveBtn, getBtn;
    TextInputEditText name, phonenumber;
    TextView nameView, phoneView;
    LinearLayout contener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        phonenumber = findViewById(R.id.phonenumber);
        saveBtn = findViewById(R.id.saveBtn);
        getBtn = findViewById(R.id.getBtn);
        nameView = findViewById(R.id.contectname);
        phoneView = findViewById(R.id.contectPhoneNumber);
        contener = findViewById(R.id.contener);


        MyDbHendler db = new MyDbHendler(MainActivity.this);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = name.getText().toString();
                String phone = phonenumber.getText().toString();
                db.addContect(names, phone);
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONArray allContects = db.getAllContects();
                for (int i = 0; i < allContects.length(); i++) {
                    View layout = getLayoutInflater().inflate(R.layout.listview, null);
                    TextView conName = layout.findViewById(R.id.contectname);
                    TextView connPhon = layout.findViewById(R.id.contectPhoneNumber);
                    try {
                    conName.setText(allContects.getJSONObject(i).getString(MyDbHendler.KEY_NMAE));
                        connPhon.setText(allContects.getJSONObject(i).getString(MyDbHendler.KEY_PHONE));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    contener.addView(layout);
                    try {
                        Log.d("DbNaren", "onCreate: " + allContects.getJSONArray(i).getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}