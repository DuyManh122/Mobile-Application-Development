package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StandardCalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StandardCalculatorFragment extends Fragment implements View.OnClickListener {
    private TextView displayResult;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;


    public StandardCalculatorFragment() {
        // Required empty public constructor
    }


    public static StandardCalculatorFragment newInstance(String param1, String param2) {
        StandardCalculatorFragment fragment = new StandardCalculatorFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_standard_calculator, container, false);

        displayResult = view.findViewById(R.id.displayResult);

        view.findViewById(R.id.button0).setOnClickListener(this);
        view.findViewById(R.id.button1).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);
        view.findViewById(R.id.button3).setOnClickListener(this);
        view.findViewById(R.id.button4).setOnClickListener(this);
        view.findViewById(R.id.button5).setOnClickListener(this);
        view.findViewById(R.id.button6).setOnClickListener(this);
        view.findViewById(R.id.button7).setOnClickListener(this);
        view.findViewById(R.id.button8).setOnClickListener(this);
        view.findViewById(R.id.button9).setOnClickListener(this);

        view.findViewById(R.id.buttonAdd).setOnClickListener(this);
        view.findViewById(R.id.buttonSubtract).setOnClickListener(this);
        view.findViewById(R.id.buttonMultiply).setOnClickListener(this);
        view.findViewById(R.id.buttonDivide).setOnClickListener(this);
        view.findViewById(R.id.buttonEquals).setOnClickListener(this);
        view.findViewById(R.id.buttonDot).setOnClickListener(this);
        view.findViewById(R.id.buttonPercent).setOnClickListener(this);
        view.findViewById(R.id.buttonInverse).setOnClickListener(this);
        view.findViewById(R.id.buttonPower).setOnClickListener(this);
        view.findViewById(R.id.buttonSquare).setOnClickListener(this);
        view.findViewById(R.id.buttonNegative).setOnClickListener(this);

        view.findViewById(R.id.buttonClearAll).setOnClickListener(this);
        view.findViewById(R.id.buttonClearElement).setOnClickListener(this);
        view.findViewById(R.id.buttonClearUnit).setOnClickListener(this);
        return view;
    }

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