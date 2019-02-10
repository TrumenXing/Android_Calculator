package com.example.arturo.calculadora;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//Java Math Expression Parser
import com.singularsys.jep.*;


public class MainActivity extends AppCompatActivity {

    private EditText topScreen;
    private EditText bottomScreen;
    private boolean equalPressed;


    private void changeResult(View v, String r){
        muestraBottomScreen(topScreen.getText().toString());
        muestraTopScreen(r);
    }

    private boolean parserSymbols(String result){

        String finalChar = result.substring(result.length()-1, result.length());

        if(finalChar.equals("+")){
            return true;
        }
        else if(finalChar.equals("-")){
            return true;
        }
        else if(finalChar.equals("/")){
            return true;
        }
        else if(finalChar.equals("*")){
            return true;
        }
        else if(finalChar.equals(".")){
            return true;
        }
        else{
            return false;
        }
    }

    private void muestraTopScreen(String s){
        topScreen.setText(s);
    }

    private void muestraBottomScreen(String s){
        bottomScreen.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topScreen = (EditText) findViewById(R.id.arribaPantalla);
        bottomScreen = (EditText) findViewById(R.id.abajoPantalla);

        //Carpeta donde está la fuente digital
        String carpetaFuente = "fuentes/digital-7.ttf";

        // Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

        // Aplicamos la fuente
        topScreen.setTypeface(fuente);
        bottomScreen.setTypeface(fuente);
        topScreen.setTextSize(50);
        bottomScreen.setTextSize(45);
        equalPressed = false;
    }

    public void deleteCharacter(View v){
        if(!equalPressed) {
            String textScreen = topScreen.getText().toString();

            if (textScreen.length() > 0 && textScreen != "0") {
                muestraTopScreen(textScreen.substring(0, textScreen.length() - 1));
            }
        }
    }

    public void clickToNumber(View v){

        if(equalPressed) {
            muestraTopScreen("");
            muestraBottomScreen("");
        }
        String screen = topScreen.getText().toString();

        if(!screen.equals("0")) {

            int id = v.getId();

            switch (id) {
                case R.id.button0: muestraTopScreen(topScreen.getText().append("0").toString()); break;
                case R.id.button1: muestraTopScreen(topScreen.getText().append("1").toString()); break;
                case R.id.button2: muestraTopScreen(topScreen.getText().append("2").toString()); break;
                case R.id.button3: muestraTopScreen(topScreen.getText().append("3").toString()); break;
                case R.id.button4: muestraTopScreen(topScreen.getText().append("4").toString()); break;
                case R.id.button5: muestraTopScreen(topScreen.getText().append("5").toString()); break;
                case R.id.button6: muestraTopScreen(topScreen.getText().append("6").toString()); break;
                case R.id.button7: muestraTopScreen(topScreen.getText().append("7").toString()); break;
                case R.id.button8: muestraTopScreen(topScreen.getText().append("8").toString()); break;
                case R.id.button9: muestraTopScreen(topScreen.getText().append("9").toString()); break;
            }
        }

        equalPressed = false;

    }

    public void clickBrand(View v){

        equalPressed = true;
        topScreen.setText(R.string.nombre);
        bottomScreen.setText(R.string.copyright);
    }

    public void clickAC(View v){
        muestraBottomScreen("");
        muestraTopScreen("");
        equalPressed = false;
    }

    public void clickP1(View v){
        muestraTopScreen(topScreen.getText().append("(").toString());
        equalPressed = false;
    }

    public void clickP2(View v){
        muestraTopScreen(topScreen.getText().append(")").toString());
        equalPressed = false;

    }

    public void clickSuma(View v){
        String result = topScreen.getText().toString();

        if(result.length() > 0 && !parserSymbols(result)){
            muestraTopScreen(topScreen.getText().append("+").toString());
        }
        equalPressed = false;
    }

    public void clickResta(View v){
        String result = topScreen.getText().toString();

        if(result.length() > 0 && !parserSymbols(result)){
            muestraTopScreen(topScreen.getText().append("-").toString());
        }
        else if(result.length() == 0){
            muestraTopScreen(topScreen.getText().append("-").toString());
        }
        equalPressed = false;
    }

    public void clickMult(View v){
        String result = topScreen.getText().toString();

        if(result.length() > 0 && !parserSymbols(result)){
            muestraTopScreen(topScreen.getText().append("*").toString());
        }
        equalPressed = false;
    }

    public void clickDivision(View v){
        String result = topScreen.getText().toString();

        if(result.length() > 0 && !parserSymbols(result)){
            muestraTopScreen(topScreen.getText().append("/").toString());
        }
        equalPressed = false;
    }

    public void clickComa(View v){
        String result = topScreen.getText().toString();

        if(result.length() > 0 && !parserSymbols(result)){
            muestraTopScreen(topScreen.getText().append(".").toString());
        }
        equalPressed = false;
    }

    public void clickEqual(View v){

        equalPressed = true;

        Jep jep = new Jep();

        if(!topScreen.getText().toString().equals("")){

            try {

                String s = topScreen.getText().toString();
                jep.parse(s);
                Object resultado = jep.evaluate();
                String result = resultado.toString();

                //Si el resultado del object double acaba en .0, se quita ese decimal
                if(result.substring(result.length()-2, result.length()).equals(".0")){
                    result = result.substring(0, result.length() - 2);
                }
                else if(result.contains("E")){
                    String[] spl = result.split("E");
                    result =  spl[0] + " E^" + spl[1];
                }


                changeResult(v, result);

            }catch(JepException e){
                muestraTopScreen("Math Error");
                muestraBottomScreen("");
            }
        }
    }

}
