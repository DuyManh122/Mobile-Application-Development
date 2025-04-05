package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ProgrammingCalculatorFragment extends Fragment implements View.OnClickListener {

    private TextView displayResult;
    private TextView tvHexValue, tvDecValue, tvOctValue, tvBinValue;
    private RadioGroup radioGroupBase;

    private String currentInput = "0";
    private long currentValue = 0;
    private String pendingOperation = "";
    private long pendingValue = 0;
    private int currentBase = 10;
    private boolean isNewInput = true;
    private boolean isLastInputOperation = false;

    public ProgrammingCalculatorFragment() {
    }


    public static ProgrammingCalculatorFragment newInstance(String param1, String param2) {
        ProgrammingCalculatorFragment fragment = new ProgrammingCalculatorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programming_calculator, container, false);

        displayResult = view.findViewById(R.id.displayResult);
        tvHexValue = view.findViewById(R.id.tvHexValue);
        tvDecValue = view.findViewById(R.id.tvDecValue);
        tvOctValue = view.findViewById(R.id.tvOctValue);
        tvBinValue = view.findViewById(R.id.tvBinValue);

        radioGroupBase = view.findViewById(R.id.radioGroupBase);



        radioGroupBase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioHex) {
                    currentBase = 16;
                } else if (checkedId == R.id.radioDec) {
                    currentBase = 10;
                } else if (checkedId == R.id.radioOct) {
                    currentBase = 8;
                } else if (checkedId == R.id.radioBin) {
                    currentBase = 2;
                }
                updateDisplay();
                updateHexButtons(currentBase);
            }
        });

        view.findViewById(R.id.btnA).setOnClickListener(this);
        view.findViewById(R.id.btnB).setOnClickListener(this);
        view.findViewById(R.id.btnC).setOnClickListener(this);
        view.findViewById(R.id.btnD).setOnClickListener(this);
        view.findViewById(R.id.btnE).setOnClickListener(this);
        view.findViewById(R.id.btnF).setOnClickListener(this);

        view.findViewById(R.id.btnAnd).setOnClickListener(this);
        view.findViewById(R.id.btnOr).setOnClickListener(this);
        view.findViewById(R.id.btnXor).setOnClickListener(this);
        view.findViewById(R.id.btnNot).setOnClickListener(this);

        view.findViewById(R.id.btnShihftLeft).setOnClickListener(this);
        view.findViewById(R.id.btnShihftRight).setOnClickListener(this);
        view.findViewById(R.id.buttonClearUnit).setOnClickListener(this);
        view.findViewById(R.id.buttonClear).setOnClickListener(this);

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
        view.findViewById(R.id.buttonNegative).setOnClickListener(this);

        updateAllValues();
        updateHexButtons(currentBase);

        return view;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button0) {
            handleDigitInput("0");
        } else if (id == R.id.button1) {
            handleDigitInput("1");
        } else if (id == R.id.button2) {
            handleDigitInput("2");
        } else if (id == R.id.button3) {
            handleDigitInput("3");
        } else if (id == R.id.button4) {
            handleDigitInput("4");
        } else if (id == R.id.button5) {
            handleDigitInput("5");
        } else if (id == R.id.button6) {
            handleDigitInput("6");
        } else if (id == R.id.button7) {
            handleDigitInput("7");
        } else if (id == R.id.button8) {
            handleDigitInput("8");
        } else if (id == R.id.button9) {
            handleDigitInput("9");
        } else if (id == R.id.btnA) {
            handleDigitInput("A");
        } else if (id == R.id.btnB) {
            handleDigitInput("B");
        } else if (id == R.id.btnC) {
            handleDigitInput("C");
        } else if (id == R.id.btnD) {
            handleDigitInput("D");
        } else if (id == R.id.btnE) {
            handleDigitInput("E");
        } else if (id == R.id.btnF) {
            handleDigitInput("F");
        }



        else if (id == R.id.buttonAdd) {
            handleOperation("+");
        } else if (id == R.id.buttonSubtract) {
            handleOperation("-");
        } else if (id == R.id.buttonMultiply) {
            handleOperation("*");
        } else if (id == R.id.buttonDivide) {
            handleOperation("÷");
        } else if (id == R.id.buttonEquals) {
            performCalculation();
            pendingOperation = "";
        } else if (id == R.id.btnAnd) {
            handleOperation("AND");
        } else if (id == R.id.btnOr) {
            handleOperation("OR");
        } else if (id == R.id.btnXor) {
            handleOperation("XOR");
        } else if (id == R.id.btnNot) {
            currentValue = ~currentValue;
            updateAllValues();
        } else if (id == R.id.btnShihftLeft) {
            handleOperation("<<");
        } else if (id == R.id.btnShihftRight) {
            handleOperation(">>");
        } else if (id == R.id.buttonClearUnit) {
            currentInput = "0";
            currentValue = 0;
            pendingOperation = "";
            pendingValue = 0;
            isNewInput = true;
            isLastInputOperation = false;
            updateAllValues();
        } else if (id == R.id.buttonClear) {
            if (currentInput.length() > 1) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            } else {
                currentInput = "0";
            }
            try {
                currentValue = Long.parseLong(currentInput, currentBase);
            } catch (NumberFormatException e) {
                currentInput = "0";
                currentValue = 0;
            }
            updateAllValues();
        } else if (id == R.id.buttonNegative) {
            currentValue = -currentValue;
            updateAllValues();
        }
    }

    private void handleDigitInput(String digit) {
        int digitValue = getDigitValue(digit);
        if (digitValue >= currentBase) {
            return;
        }

        if (isNewInput) {
            currentInput = digit;
            isNewInput = false;
        } else {
            currentInput += digit;
        }

        isLastInputOperation = false;

        try {
            currentValue = Long.parseLong(currentInput, currentBase);
        } catch (NumberFormatException e) {
            currentInput = "0";
            currentValue = 0;
        }

        updateAllValues();
    }

    private int getDigitValue(String digit) {
        if (digit.length() == 1) {
            char c = digit.charAt(0);
            if (c >= '0' && c <= '9') {
                return c - '0';
            } else if (c >= 'A' && c <= 'F') {
                return c - 'A' + 10;
            }
        }
        return 0;
    }

    private void handleOperation(String operation) {
        if (!isLastInputOperation) {
            performCalculation();
        }

        pendingOperation = operation;
        pendingValue = currentValue;
        isNewInput = true;
        isLastInputOperation = true;
    }

    private void performCalculation() {
        if (pendingOperation.isEmpty()) {
            return;
        }

        long result = pendingValue;

        switch (pendingOperation) {
            case "+":
                result += currentValue;
                break;
            case "-":
                result -= currentValue;
                break;
            case "*":
                result *= currentValue;
                break;
            case "÷":
                if (currentValue != 0) {
                    result /= currentValue;
                } else {
                    result = 0;
                }
                break;
            case "AND":
                result &= currentValue;
                break;
            case "OR":
                result |= currentValue;
                break;
            case "XOR":
                result ^= currentValue;
                break;
            case "<<":
                result <<= currentValue;
                break;
            case ">>":
                result >>= currentValue;
                break;
        }

        currentValue = result;
        isNewInput = true;
        updateAllValues();
    }

    private void updateAllValues() {
        updateDisplay();
        updateConversionDisplay();
    }

    private void updateDisplay() {
        String displayText;
        switch (currentBase) {
            case 16:
                displayText = Long.toHexString(currentValue).toUpperCase();
                break;
            case 8:
                displayText = Long.toOctalString(currentValue);
                break;
            case 2:
                displayText = Long.toBinaryString(currentValue);
                break;
            default:
                displayText = Long.toString(currentValue);
                break;
        }

        currentInput = displayText;
        displayResult.setText(displayText);
    }

    private void updateConversionDisplay() {
        tvHexValue.setText("Hex: " + Long.toHexString(currentValue).toUpperCase());
        tvDecValue.setText("Dec: " + Long.toString(currentValue));
        tvOctValue.setText("Oct: " + Long.toOctalString(currentValue));
        tvBinValue.setText("Bin: " + Long.toBinaryString(currentValue));
    }

    private void updateHexButtons(int currentBase) {
        View view = getView();
        if (view == null) return;

        view.findViewById(R.id.btnA).setEnabled(currentBase > 10);
        view.findViewById(R.id.btnB).setEnabled(currentBase > 11);
        view.findViewById(R.id.btnC).setEnabled(currentBase > 12);
        view.findViewById(R.id.btnD).setEnabled(currentBase > 13);
        view.findViewById(R.id.btnE).setEnabled(currentBase > 14);
        view.findViewById(R.id.btnF).setEnabled(currentBase > 15);

        view.findViewById(R.id.button2).setEnabled(currentBase > 2);
        view.findViewById(R.id.button3).setEnabled(currentBase > 3);
        view.findViewById(R.id.button4).setEnabled(currentBase > 4);
        view.findViewById(R.id.button5).setEnabled(currentBase > 5);
        view.findViewById(R.id.button6).setEnabled(currentBase > 6);
        view.findViewById(R.id.button7).setEnabled(currentBase > 7);
        view.findViewById(R.id.button8).setEnabled(currentBase > 8);
        view.findViewById(R.id.button9).setEnabled(currentBase > 9);
    }
}