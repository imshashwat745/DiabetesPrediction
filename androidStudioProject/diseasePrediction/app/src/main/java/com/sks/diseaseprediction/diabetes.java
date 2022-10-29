package com.sks.diseaseprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class diabetes extends AppCompatActivity {

    EditText pregnancy,glucose,age,insulin,dpf,bmi,bp,skin_thickness;
    TextView result;
    String url="https://disease-prediction-sks.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);
        pregnancy=findViewById(R.id.pregnancy);
        glucose=findViewById(R.id.glucose);
        age=findViewById(R.id.age);
        insulin=findViewById(R.id.insulin);
        dpf=findViewById(R.id.dpf);
        bmi=findViewById(R.id.bmi);
        bp=findViewById(R.id.bp);
        skin_thickness=findViewById(R.id.skin_thickness);
        result=findViewById(R.id.result);
    }
    @SuppressLint("ResourceAsColor")
    public void calculate(View view){
        result.setText("Please Wait...");
        String preg=pregnancy.getText().toString();
        if(preg.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String a=age.getText().toString();
        if(a.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String glu=glucose.getText().toString();
        if(glu.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String insu=insulin.getText().toString();
        if(insu.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String b_m_i=bmi.getText().toString();
        if(b_m_i.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String d_p_f=dpf.getText().toString();
        if(d_p_f.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String b_p=bp.getText().toString();
        if(b_p.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        String st=skin_thickness.getText().toString();
        if(st.isEmpty()){
            result.setText("Error:All fields are required!");
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String ans=jsonObject.getString("Result");
                    double val=Double.parseDouble(ans);
                    val*=100;
                    result.setText("There are "+((int)val)+"% chances of Diabetes");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(diabetes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("pregnancy",preg);
                params.put("age",a);
                params.put("dpf",d_p_f);
                params.put("bmi",b_m_i);
                params.put("bp",b_p);
                params.put("glucose",glu);
                params.put("insulin",insu);
                params.put("skin_thickness",st);
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(diabetes.this);
        queue.add(stringRequest);
    }
}