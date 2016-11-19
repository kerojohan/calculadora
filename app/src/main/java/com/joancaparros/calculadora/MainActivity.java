package com.joancaparros.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String operant1="";
    private String operant2="";
    private String operacio="";
    private String resultat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicialització secció textual de la calculadora
        final EditText calcText = (EditText) findViewById(R.id.calcText);
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

        //secció tractament de les accions click
        value0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               operant1 = operant1+"0";
                calcText.setText(operant1);


            }
        });

        value1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"1";
                calcText.setText(operant1);
            }
        });
        value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"2";
                calcText.setText(operant1);
            }
        });
        value3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"3";
                calcText.setText(operant1);
            }
        });
        value4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"4";
                calcText.setText(operant1);
            }
        });
        value5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"5";
                calcText.setText(operant1);
            }
        });
        value6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"6";
                calcText.setText(operant1);
            }
        });
        value7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"7";
                calcText.setText(operant1);
            }
        });
        value8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"8";
                calcText.setText(operant1);
            }
        });
        value9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"9";
                calcText.setText(operant1);
            }
        });

        operationResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String evalucio=evalOperation(calcText.getText().toString());
                calcText.setText(evalucio);

            }
        });

        operationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"+";
                calcText.setText(operant1);
            }
        });
        operationSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"-";
                calcText.setText(operant1);
            }
        });

        operationMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"*";
                calcText.setText(operant1);
            }
        });
        operationDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = operant1+"/";
                calcText.setText(operant1);
            }
        });
        operationDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = "0";
                calcText.setText(operant1);
            }
        });
        operationAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operant1 = "0";
                calcText.setText(operant1);
            }
        });



    }

    private String evalOperation(String cadenaOperacio){
        System.out.println(cadenaOperacio);
        double resultat = eval(cadenaOperacio);
        String total2 = String.valueOf(Math.round(resultat));

        return total2;
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}
