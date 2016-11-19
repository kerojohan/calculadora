package com.joancaparros.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private String currentOperation="";
    private Integer resultat=0;
    private Integer partialresult=0;
    private StringBuilder screen = new StringBuilder();
    private TextView calcText;
    private Boolean error=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicialització secció textual de la calculadora
        calcText = (TextView) findViewById(R.id.calcText);
        //Inicialització butons referents a valors
        Button value0 = (Button) findViewById(R.id.button0);
        Button value1 = (Button) findViewById(R.id.button1);
        Button value2 = (Button) findViewById(R.id.button2);
        Button value3 = (Button) findViewById(R.id.button3);
        Button value4 = (Button) findViewById(R.id.button4);
        Button value5 = (Button) findViewById(R.id.button5);
        Button value6 = (Button) findViewById(R.id.button6);
        Button value7 = (Button) findViewById(R.id.button7);
        Button value8 = (Button) findViewById(R.id.button8);
        Button value9 = (Button) findViewById(R.id.button9);
        //Inicialització butons referents a operacions
        Button operationAdd = (Button) findViewById(R.id.buttonAdd);
        Button operationSub = (Button) findViewById(R.id.buttonSubs);
        Button operationMult = (Button) findViewById(R.id.buttonMult);
        Button operationDiv = (Button) findViewById(R.id.buttonDiv);
        Button operationChangeSign = (Button) findViewById(R.id.buttonChangeSign);
        Button operationResult = (Button) findViewById(R.id.buttonResult);
        Button operationAC = (Button) findViewById(R.id.buttonAC);

        //secció tractament de les accions click en numeros
        value0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("0");
            }
        });
        value1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("1");
            }
        });
        value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("2");
            }
        });
        value3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("3");
            }
        });
        value4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("4");
            }
        });
        value5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("5");
            }
        });
        value6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("6");
            }
        });
        value7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("7");
            }
        });
        value8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("8");
            }
        });
        value9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("9");
            }
        });

        //secció tractament de les accions click en operacions + - * /
        operationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperation("+"); }
        });
        operationSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperation("-"); }
        });
        operationMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperation("*");
            }
        });
        operationDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperation("/");
            }
        });

        //tractament click botó AC
        operationAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanScreen();
            }
        });

        //tractament click botó =
        operationResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentOperation="=";
                String evaluacio=evalOperation(calcText.getText().toString());
                //es pot produir un error en cas de divisions per 0
                if (error){
                    evaluacio="ERR!";
                    calcText.setText(evaluacio);
                    screen.setLength(0);
                    error=false;
                }else{
                    calcText.setText(evaluacio);
                    screen.setLength(0);
                    screen.append(evaluacio);
                }
            }
        });
    }


    private void setOperator(String operator){
        //si després d'un resultat introdueixen un altre número es neteja el resultat parcial
        if(screen.length()>0 && currentOperation=="="){
            cleanScreen();
        }
        screen.append(operator);
        calcText.setText(screen.toString());
        currentOperation="";
    }

    private void cleanScreen(){
        System.out.println("netejant pantalla");
        screen.setLength(0);
        calcText.setText(screen.toString());
    }

    private void setOperation(String operation){
        //en el cas que s'hagi introduit una operació però s'escolleig un altre es reemplaça
        // sempre i quan l'expressió contingui +-/*
        if(currentOperation!="" && screen.toString().matches(".*[+-/*].*")){
            screen.setLength(screen.length()-1);
        }
        currentOperation=operation;
        System.out.println("currentOperation:"+currentOperation);
        screen.append(currentOperation);
        calcText.setText(screen.toString());
    }

    private String evalOperation(String cadenaOperacio){
        System.out.println(cadenaOperacio);
        int resultat = calc(cadenaOperacio);
        String total2 = String.valueOf(resultat);
        return total2;
    }

    public int calc(String string){
        int result=0;
        String numbers="0123456789";
        for (int i=0;i<string.length();i++){
            if (numbers.contains(string.charAt(i)+"")){
                result=result*10+(Integer.parseInt(string.charAt(i)+""));
            }
            else {
                if (string.charAt(i)=='+'){ result+=calc(string.substring(i+1));}
                if (string.charAt(i)=='-'){ result-=calc(string.substring(i+1));}
                if (string.charAt(i)=='*'){ result*=calc(string.substring(i+1));}
                if (string.charAt(i)=='/'){ try{result/=calc(string.substring(i+1));}
                catch (ArithmeticException e){
                    System.err.println("ERROR!");
                    error=true;
                }
                }
                break;
            }
        }
        return result;
    }

}
