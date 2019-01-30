package htl.grieskirchen.androidstudio.vhoerzi16woche17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private List<String[]> satzteile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initializeUI() {
        button = (Button) findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        String json = null;
        try {
            InputStream is = getAssets().open("vokabular.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject vokabular = obj.getJSONObject("vokabular");
            Iterator x = vokabular.keys();
            JSONArray jsonArray= new JSONArray();

            while(x.hasNext()){
                jsonArray.put(vokabular.get(String.valueOf(x.next())));
            }

            satzteile = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                String[] split = String.valueOf(jsonArray.get(i)).split(",");
                split[0] = split[0].replace("[","");
                split[split.length-1] = split[split.length-1].replace("]","");
                satzteile.add(Arrays.stream(split).map(str -> str.replace("\"", "")).toArray(String[]::new));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
       textView.setText("");
        Iterator<String[]> iterator = satzteile.iterator();
        while(iterator.hasNext()){
            String[] next = iterator.next();
            textView.append(next[(int) Math.floor(Math.random()*next.length)]+" ");
        }
    }
}
