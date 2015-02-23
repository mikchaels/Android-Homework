package com.example.soloviev.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by slezgouka on 2/4/2015.
 */

public class CalculatorActivity extends Activity {
    private RadioGroup operands;
    TextView result;
    EditText operand_first;
    EditText operand_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calc);
        operand_first = (EditText) findViewById(R.id.first_number);
        operand_second = (EditText) findViewById(R.id.second_number);
        result = (TextView) findViewById(R.id.result);
        operands = (RadioGroup) findViewById(R.id.radio_group);


        Button performButton = (Button) findViewById(R.id.perform_operation);
        performButton.setOnClickListener(new Calculate());


    }

    class Calculate implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            try {
                switch (operands.getCheckedRadioButtonId()) {
                    case View.NO_ID:
                        Toast.makeText(
                                CalculatorActivity.this, "click_operand", Toast.LENGTH_SHORT
                        ).show();
                        return;
                    case R.id.plus_operation:
                        setResult(new Calculator(getDoubleArgument(operand_first), getDoubleArgument(operand_second)).summ());
                        return;
                    case R.id.minus_operation:
                        setResult(new Calculator(getDoubleArgument(operand_first), getDoubleArgument(operand_second)).subtraction());
                        return;
                    case R.id.multiply_operation:
                        setResult(new Calculator(getDoubleArgument(operand_first), getDoubleArgument(operand_second)).multiply());
                        return;
                    case R.id.devide_operation:
                        setResult(new Calculator(getDoubleArgument(operand_first), getDoubleArgument(operand_second)).division());
                        return;
                    default:
                        throw new RuntimeException("HZ_operand");
                }
            } catch (IllegalArgumentException e) {
                if (getDoubleArgument(operand_second) == 0) {
                    divisionByZero();
                } else {
                    Toast.makeText(
                            CalculatorActivity.this, "Illegal_Arguments", Toast.LENGTH_SHORT
                    ).show();
                }
            }

        }
    }

    public void setResult(double doubleResult) {
        String stringResult;
        stringResult = getString(R.string.result_format, doubleResult);
        result.setText(stringResult);
    }

    public double getDoubleArgument(EditText editText) {
        CharSequence text = editText.getText();
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException();
        } else {
            try {
                return Double.parseDouble(text.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("result", result.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result.setText(savedInstanceState.getCharSequence("result"));
    }

    private void divisionByZero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("division")
                .setMessage("division by zero")
                .setNeutralButton("ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
