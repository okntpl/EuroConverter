package com.okantopal.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.lang.*;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView usdText;
    TextView jpyText;
    TextView chfText;
    TextView gbpText;
    TextView cadText;

    EditText euroRate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        jpyText = findViewById(R.id.jpyText);
        usdText = findViewById(R.id.usdText);
        chfText = findViewById(R.id.chfText);
        gbpText = findViewById(R.id.gbpText);

        euroRate = findViewById(R.id.editText);


    }

    public void getRates(View view)
    {


        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=099aad49ed556f18a009ec7873e07f97&format=1";
            downloadData.execute(url);
        }catch (Exception e)
        {

        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data>0)
                {
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }
                return result;

            }catch (Exception e){
              return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(4);
            df.setMinimumFractionDigits(4);


          try {




              if(euroRate.getText().toString().trim().length()==0)
              {
                  euroRate.setText("1");
              }

              Float euro = Float.parseFloat(euroRate.getText().toString());

              JSONObject jsonObject = new JSONObject(s);
              String rates =jsonObject.getString("rates");

              JSONObject jsonObject1 = new JSONObject(rates);
              String turkishlira = jsonObject1.getString("TRY");

              Float lirarate = Float.parseFloat(turkishlira);
              tryText.setText("TRY: "+ String.valueOf(df.format(lirarate*euro)));


              JSONObject jsonObject2 = new JSONObject(rates);
              String dolar = jsonObject2.getString("USD");
              Float dolarrate = Float.parseFloat(dolar);
              usdText.setText("USD: "+String.valueOf(df.format(dolarrate*euro)));

              JSONObject jsonObject3 = new JSONObject(rates);
              String canadadolar = jsonObject3.getString("CAD");
              Float canadarate = Float.parseFloat(canadadolar);
              cadText.setText("CAD: "+String.valueOf(df.format(canadarate*euro)));

              JSONObject jsonObject4 = new JSONObject(rates);
              String yen = jsonObject4.getString("JPY");
              Float yenrate = Float.parseFloat(yen);
              jpyText.setText("JPY: "+String.valueOf(df.format(yenrate*euro)));

              JSONObject jsonObject5 = new JSONObject(rates);
              String frank = jsonObject5.getString("CHF");
              Float frankrate = Float.parseFloat(frank);
              chfText.setText("CHF: "+String.valueOf(df.format(frankrate*euro)));

              JSONObject jsonObject6 = new JSONObject(rates);
              String pound = jsonObject6.getString("GBP");
              Float poundrate = Float.parseFloat(pound);

              gbpText.setText("GBP: "+String.valueOf(df.format(poundrate*euro)));

          }catch (Exception e)
          {

          }
        }
    }


}
