package com.joancaparros.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String currentOperation = "";
    private Integer op1 = 0;
    private Integer op2 = 0;
    private Integer result = 0;
    private StringBuilder screen = new StringBuilder();
    private StringBuilder operator1 = new StringBuilder();
    private TextView calcText;
    private TextView currentNum;
    private ArrayList<String> operationsList = new ArrayList<>();
    private Boolean error=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicialització secció textual de la calculadora
        calcText = (TextView) findViewById(R.id.calcText);
        currentNum = (TextView) findViewById(R.id.currentNum);
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
        Button showHistory = (Button) findViewById(R.id.buttonHistory);
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
                setOperation("+");
            }
        });
        operationSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperation("-");
            }
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
        //tractament canvi de signe
        operationChangeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si hi ha valor dins del currentNum procedim a canviar-li el signe
                if (currentNum.getText().length() > 0) {
                    Integer valor = Integer.parseInt(currentNum.getText().toString()) * (-1);
                    operator1.setLength(0);
                    operator1.append(valor);
                    currentNum.setText(operator1);
                }
            }
        });
        //tractament click botó AC
        operationAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //es netegen les dues seccions de la pantalla i s'inicialitzen variables
                cleanScreen();
                cleanPartialScreen();
                result = 0;
                currentOperation="";
            }
        });
        //tractament click botó =
        operationResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //el botó igual no s'habilita fins que no s'ha entrat una operació
                if (currentOperation != "" && currentNum.length()>0) {
                    evalOperation();
                }
            }
        });
        showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HistoryActivity.class);
                intent.putExtra("listOperations", operationsList );
                startActivity(intent);
            }
        });
    }

    private void setOperator(String operator) {
        //Si es prem un numero quan hi havia un resultat aprofitable anterior
        //es neteja la pantalla i es descarta el valor anterior
        if (currentOperation == "") {
            cleanScreen();
            error=false;
        }
        operator1.append(operator);
        currentNum.setText(operator1);
    }


    private void setOperation(String operation) {
        if (currentOperation == ""
                && (currentNum.length()>0 || calcText.length()>0)
                && (error==false)
                ) {
            currentOperation = operation;
            //cal que s'hagi definit un valor anterior per marcar una operació
            if (operator1.toString() != "") {
                screen.append(operator1);
                screen.append(operation);
                cleanPartialScreen();
                calcText.setText(screen.toString());
            } else if (calcText.length() > 0) {
                //si es decideix aprofitar el resultat de la operació anterior
                //netegem pantalla i coloquem aquest resultat més l'operació actual
                cleanScreen();
                screen.append(result);
                screen.append(operation);
                cleanPartialScreen();
                calcText.setText(screen.toString());
            }
        }else if(calcText.length()>0 && (error==false)){
            //permeto que canviin la operació quan ja han entrat el segon operand
            currentOperation = operation;
            String valdisplay1 = calcText.getText().toString();
            cleanScreen();
            screen.append(valdisplay1.substring(0, valdisplay1.length() - 1));
            screen.append(operation);
            calcText.setText(screen.toString());
        }

    }

    //Neteja del TextView principal
    private void cleanScreen() {
        screen.setLength(0);
        screen.append("");
        calcText.setText(screen.toString());
    }

    //Neteja del TextView operant actual
    private void cleanPartialScreen() {
        operator1.setLength(0);
        currentNum.setText(operator1.toString());
    }

    //Evaluació i de les operacions i de la llista
    private void evalOperation(){
        String valdisplay1 = calcText.getText().toString();
        String valdisplay2 = currentNum.getText().toString();
        try {
            op1 = Integer.parseInt(valdisplay1.substring(0, valdisplay1.length() - 1));
            op2 = Integer.parseInt(valdisplay2);
            screen.append(valdisplay2);
            screen.append("=");
            screen.append(calc(op1, op2));
        } catch (Exception e) {
            //Deso en el registre les operacions amb resultats
            //erronis per excedir la capacitat delInteger
            error=true;
            cleanScreen();
            screen.append(valdisplay1);
            screen.append(valdisplay2);
            screen.append("=");
            screen.append("Integer overflow");
        }
        //afegeixo la operació ben formatada a la llista d'operacions
        addToHistoryList(screen.toString());
        calcText.setText(screen.toString());
        cleanPartialScreen();
        currentOperation="";
    }

    //Afegir element a la llista, treient aquells més antics per sempre tenir 20 elements
    private void addToHistoryList(String operation){
        operationsList.add(operation);
        if(operationsList.size()>20)
            operationsList.remove(0);
    }

    //Execució de càlculs
    private String calc(Integer op1, Integer op2) {
        switch (currentOperation) {
            case "+":
                result =(op1 + op2);
                return result.toString();
            case "-":
                result =(op1 - op2);
                return result.toString();
            case "*":
                result =(op1 * op2);
                return result.toString();
            case "/":
                try {
                    //contemplo les pecularitats de la divisió per 0
                    if((op1>0) && op2==0) {
                        //   result=0;
                        error=true;
                        return "∞";}
                    else if((op1<0) && op2==0) {
                        //   result=0;
                        error=true;
                        return "-∞";}
                    if (op1==0 && op2==0) {
                        error=true;
                        //  result=0;
                        return "ERROR!";}
                    else{ result =(op1 / op2);
                        return result.toString();}
                } catch (Exception e) {
                    System.err.println(e);
                }
            default:
                return "";
        }
    }
}
