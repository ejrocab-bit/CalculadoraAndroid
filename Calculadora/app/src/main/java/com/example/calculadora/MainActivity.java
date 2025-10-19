package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private String numeroActual = "";
    private String operador = "";
    private double primerNumero = 0;
    private boolean acabaDeCalcular = false; // Diu si hem donat al = ( fi de calcul )
    private String operadorMostrar = "";
    private double memoria = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);

        // Números
        ImageButton btn0 = findViewById(R.id.btn0);
        ImageButton btn1 = findViewById(R.id.btn1);
        ImageButton btn2 = findViewById(R.id.btn2);
        ImageButton btn3 = findViewById(R.id.btn3);
        ImageButton btn4 = findViewById(R.id.btn4);
        ImageButton btn5 = findViewById(R.id.btn5);
        ImageButton btn6 = findViewById(R.id.btn6);
        ImageButton btn7 = findViewById(R.id.btn7);
        ImageButton btn8 = findViewById(R.id.btn8);
        ImageButton btn9 = findViewById(R.id.btn9);

        // Operacions
        ImageButton btnSumar = findViewById(R.id.btnSumar);
        ImageButton btnRestar = findViewById(R.id.btnRestar);
        ImageButton btnMultiplicar = findViewById(R.id.btnMultiplicar);
        ImageButton btnDividir = findViewById(R.id.btnDividir);
        ImageButton btnPunto = findViewById(R.id.btnPunto);
        ImageButton btnIgual = findViewById(R.id.btnIgual);

        // Memory
        Button btnC = findViewById(R.id.btnC);
        Button btnMn = findViewById(R.id.btnMn);
        Button btnMp = findViewById(R.id.btnMp);
        Button btnMc = findViewById(R.id.btnMc);
        Button btnMr = findViewById(R.id.btnMr);

        // Listener dels Números
        View.OnClickListener listenerNumero = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = "";
                int id = v.getId();

                if (id == R.id.btn0) numero = "0";
                else if (id == R.id.btn1) numero = "1";
                else if (id == R.id.btn2) numero = "2";
                else if (id == R.id.btn3) numero = "3";
                else if (id == R.id.btn4) numero = "4";
                else if (id == R.id.btn5) numero = "5";
                else if (id == R.id.btn6) numero = "6";
                else if (id == R.id.btn7) numero = "7";
                else if (id == R.id.btn8) numero = "8";
                else if (id == R.id.btn9) numero = "9";

                // Si acabem de calcular (hem acabat amb =), comença una nova operació
                if (acabaDeCalcular) {
                    numeroActual = numero;
                    operador = "";
                    operadorMostrar = "";
                    primerNumero = 0;
                    acabaDeCalcular = false;
                } else {
                    // Afegir el número
                    if (numeroActual.equals("0") || numeroActual.isEmpty()) {
                        numeroActual = numero;
                    } else {
                        numeroActual += numero;
                    }
                }

                // Mostrar operació completa si hi ha operador ( El String com tal al visor )
                if (!operadorMostrar.isEmpty() && primerNumero != 0) {
                    String primerNumeroStr;
                    if (primerNumero != (long) primerNumero) {
                        primerNumeroStr = String.valueOf(primerNumero);
                    } else {
                        primerNumeroStr = String.valueOf((long) primerNumero);
                    }
                    screen.setText(primerNumeroStr + " " + operadorMostrar + " " + numeroActual);
                } else {
                    screen.setText(numeroActual);
                }
            }
        };

        // Listener pel punt decimal
        View.OnClickListener listenerPunto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Només afegir punt si no n'hi ha ja
                if (!numeroActual.contains(".")) {
                    if (numeroActual.isEmpty()) {
                        numeroActual = "0.";
                    } else {
                        numeroActual += ".";
                    }
                    screen.setText(numeroActual);
                }
            }
        };

        // Listener per les operacions
        // En cas de que la operació acabi, i se introdueixi un nou número, aquesta sera nova.
        // Pero, si se li dona després del resultat a un simbol, la operacó continuará amb el resultat anterior
        View.OnClickListener listenerOperacio = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si acabem de calcular, continuar amb el resultat
                if (acabaDeCalcular) {
                    acabaDeCalcular = false;
                }
                // Si ja hi ha una operació pendent, calcular primer
                else if (!operador.isEmpty() && !numeroActual.isEmpty()) {
                    calcular();
                }

                // Guardar el primer número
                if (!numeroActual.isEmpty()) {
                    primerNumero = Double.parseDouble(numeroActual);
                }

                // Guardar l'operador
                int id = v.getId();
                if (id == R.id.btnSumar) {
                    operador = "+";
                    operadorMostrar = "+";
                } else if (id == R.id.btnRestar) {
                    operador = "-";
                    operadorMostrar = "-";
                } else if (id == R.id.btnMultiplicar) {
                    operador = "*";
                    operadorMostrar = "×";
                } else if (id == R.id.btnDividir) {
                    operador = "/";
                    operadorMostrar = "÷";
                }

                // Mostrar en pantalla
                screen.setText(numeroActual + " " + operadorMostrar);
                numeroActual = ""; // Buidar per rebre el segon número
            }
        };

        // Listener pel botó igual
        View.OnClickListener listenerIgual = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!operador.isEmpty() && !numeroActual.isEmpty()) {
                    calcular();
                    operador = "";
                    operadorMostrar = "";
                    acabaDeCalcular = true;
                }
            }
        };

        // Botó C Neteja total ( Tot a 0 )
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroActual = "0";
                operador = "";
                operadorMostrar = "";
                primerNumero = 0;
                acabaDeCalcular = false;
                screen.setText("0");
            }
        });

        // M+
        btnMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numeroActual.isEmpty()) {
                    memoria += Double.parseDouble(numeroActual);
                }
            }
        });

        // M-
        btnMn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numeroActual.isEmpty()) {
                    memoria -= Double.parseDouble(numeroActual);
                }
            }
        });

        // MC
        btnMc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoria = 0;
            }
        });

        // MR
        btnMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memoria == (long) memoria) {
                    numeroActual = String.valueOf((long) memoria);
                } else {
                    numeroActual = String.valueOf(memoria);
                }
                screen.setText(numeroActual);
                acabaDeCalcular = true;
            }
        });

        // Assignar listeners als botons dels números i al punt
        btn0.setOnClickListener(listenerNumero);
        btn1.setOnClickListener(listenerNumero);
        btn2.setOnClickListener(listenerNumero);
        btn3.setOnClickListener(listenerNumero);
        btn4.setOnClickListener(listenerNumero);
        btn5.setOnClickListener(listenerNumero);
        btn6.setOnClickListener(listenerNumero);
        btn7.setOnClickListener(listenerNumero);
        btn8.setOnClickListener(listenerNumero);
        btn9.setOnClickListener(listenerNumero);

        btnPunto.setOnClickListener(listenerPunto);

        // Assignar listeners a les operacions i al igual
        btnSumar.setOnClickListener(listenerOperacio);
        btnRestar.setOnClickListener(listenerOperacio);
        btnMultiplicar.setOnClickListener(listenerOperacio);
        btnDividir.setOnClickListener(listenerOperacio);

        btnIgual.setOnClickListener(listenerIgual);

        // Els listeners de les Memòries ya van amb el SetOnclick cada una
    }

    // Mètode per calcular el resultat
    private void calcular() {
        if (operador.isEmpty() || numeroActual.isEmpty()) {
            return;
        }

        double segonNumero = Double.parseDouble(numeroActual);
        double resultat = 0;

        switch (operador) {
            case "+":
                resultat = primerNumero + segonNumero;
                break;
            case "-":
                resultat = primerNumero - segonNumero;
                break;
            case "*":
                resultat = primerNumero * segonNumero;
                break;
            case "/":
                if (segonNumero != 0) {
                    resultat = primerNumero / segonNumero;
                } else {
                    screen.setText("Error: no pots dividir entre 0 :(");
                    numeroActual = "";
                    operador = "";
                    operadorMostrar = "";
                    primerNumero = 0;
                    return;
                }
                break;
        }

        // Mostrar el resultat
        if (resultat == (long) resultat) {
            numeroActual = String.valueOf((long) resultat);
        } else {
            numeroActual = String.valueOf(resultat);
        }

        screen.setText(numeroActual);
        primerNumero = resultat; // Guardar per operacions encadenades ( Resultat + operacio + número que posis )
    }
}