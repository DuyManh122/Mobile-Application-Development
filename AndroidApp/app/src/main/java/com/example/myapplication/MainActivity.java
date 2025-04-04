package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView displayResult;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        displayResult = findViewById(R.id.displayResult);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonDot).setOnClickListener(this);
        findViewById(R.id.buttonPercent).setOnClickListener(this);
        findViewById(R.id.buttonInverse).setOnClickListener(this);
        findViewById(R.id.buttonPower).setOnClickListener(this);
        findViewById(R.id.buttonSquare).setOnClickListener(this);
        findViewById(R.id.buttonNegative).setOnClickListener(this);

        findViewById(R.id.buttonClearAll).setOnClickListener(this);
        findViewById(R.id.buttonClearElement).setOnClickListener(this);
        findViewById(R.id.buttonClearUnit).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button0 || id == R.id.button1 || id == R.id.button2 ||
                id == R.id.button3 || id == R.id.button4 || id == R.id.button5 ||
                id == R.id.button6 || id == R.id.button7 || id == R.id.button8 ||
                id == R.id.button9) {

            if (isNewOperation) {
                currentInput = "";
                isNewOperation = false;
            }

            String digit = ((Button) view).getText().toString();
            if (currentInput.equals("0")) {
                currentInput = digit;
            } else {
                currentInput += digit;
            }
            displayResult.setText(currentInput);
        }

        else if (id == R.id.buttonDot) {
            if (isNewOperation) {
                currentInput = "0";
                isNewOperation = false;
            }

            if (!currentInput.contains(".")) {
                currentInput += ".";
            }
            displayResult.setText(currentInput);
        }

        else if (id == R.id.buttonAdd || id == R.id.buttonSubtract ||
                id == R.id.buttonMultiply || id == R.id.buttonDivide) {

            if (!currentInput.isEmpty()) {
                firstOperand = Double.parseDouble(currentInput);
                operator = ((Button) view).getText().toString();
                isNewOperation = true;
            }
        }

        else if (id == R.id.buttonEquals) {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "÷":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            displayResult.setText("Divide by zero");
                            currentInput = "";
                            operator = "";
                            isNewOperation = true;
                            return;
                        }
                        break;
                }

                if (result == (long) result) {
                    currentInput = String.valueOf((long) result);
                } else {
                    currentInput = String.valueOf(result);
                }

                displayResult.setText(currentInput);
                operator = "";
                isNewOperation = true;
            }
        }

        else if (id == R.id.buttonClearAll) {
            currentInput = "0";
            operator = "";
            firstOperand = 0;
            isNewOperation = true;
            displayResult.setText(currentInput);
        }

        else if (id == R.id.buttonClearElement) {
            currentInput = "0";
            displayResult.setText(currentInput);
        }

        else if (id == R.id.buttonClearUnit) {
            if (currentInput.length() > 1) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            } else {
                currentInput = "0";
            }
            displayResult.setText(currentInput);
        }

        else if (id == R.id.buttonPercent) {
            if (!currentInput.isEmpty()) {
                double value = Double.parseDouble(currentInput);
                value = value / 100;

                if (value == (long) value) {
                    currentInput = String.valueOf((long) value);
                } else {
                    currentInput = String.valueOf(value);
                }

                displayResult.setText(currentInput);
            }
        }

        else if (id == R.id.buttonInverse) {
            if (!currentInput.isEmpty() && Double.parseDouble(currentInput) != 0) {
                double value = Double.parseDouble(currentInput);
                value = 1 / value;

                if (value == (long) value) {
                    currentInput = String.valueOf((long) value);
                } else {
                    currentInput = String.valueOf(value);
                }

                displayResult.setText(currentInput);
                isNewOperation = true;
            } else {
                displayResult.setText("Divide by zero");
                currentInput = "";
                isNewOperation = true;
            }
        }

        else if (id == R.id.buttonPower) {
            if (!currentInput.isEmpty()) {
                double value = Double.parseDouble(currentInput);
                value = value * value;

                if (value == (long) value) {
                    currentInput = String.valueOf((long) value);
                } else {
                    currentInput = String.valueOf(value);
                }

                displayResult.setText(currentInput);
                isNewOperation = true;
            }
        }

        else if (id == R.id.buttonSquare) {
            if (!currentInput.isEmpty() && Double.parseDouble(currentInput) >= 0) {
                double value = Double.parseDouble(currentInput);
                value = Math.sqrt(value);

                if (value == (long) value) {
                    currentInput = String.valueOf((long) value);
                } else {
                    currentInput = String.valueOf(value);
                }

                displayResult.setText(currentInput);
                isNewOperation = true;
            } else {
                displayResult.setText("Error");
                currentInput = "";
                isNewOperation = true;
            }
        }

        else if (id == R.id.buttonNegative) {
            if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                if (currentInput.charAt(0) == '-') {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                displayResult.setText(currentInput);
            }
        }
    }
}