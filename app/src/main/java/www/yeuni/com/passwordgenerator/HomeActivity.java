package www.yeuni.com.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mateware.snacky.Snacky;
import es.dmoral.toasty.Toasty;


public class HomeActivity extends AppCompatActivity {
    private Spinner spinner;
    CheckBox capitalLetter, lowercaseLetter, numbers, symbols;
    TextView passwordDisplay;
    Button passGenerate, passClear;
    int MAX_CHAR = 0;
    LinearLayout linearCopy;

    private static final String NUMBERS = "0123456789";
    private static final String UPPER_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIALCHARACTERS = "!@#$%^&*()_-+=<>?/{}~|'.[]";
    Button CopyPass;
    Object clipboardService ;
     ClipboardManager clipboardManager;



    final List<String> categories = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set theme before setContentView




        setContentView(R.layout.activity_home);

        categories.add("8");
        categories.add("12");
        categories.add("16");
        categories.add("18");





        // Get clipboard manager object.
        clipboardService =getSystemService(CLIPBOARD_SERVICE);
        clipboardManager =(ClipboardManager)clipboardService;


        capitalLetter = findViewById(R.id.capital_letter);
        lowercaseLetter = findViewById(R.id.lowercase_letter);
        numbers = findViewById(R.id.numbers);
        symbols = findViewById(R.id.symbols);
        CopyPass = findViewById(R.id.CopyPass);


        spinner = findViewById(R.id.spinner);
        linearCopy = findViewById(R.id.linearCopy);

        passGenerate = findViewById(R.id.passwordGenerate);
        passClear =  findViewById(R.id.passwordClear);
        passwordDisplay =  findViewById(R.id.passwordDisplay);


        ArrayAdapter<String> dataAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));


                if (categories.get(position).equals("8")) {
                    MAX_CHAR = 8;
                    Toast.makeText(HomeActivity.this, "MAX_CHAR: "+MAX_CHAR, Toast.LENGTH_SHORT).show();
                }
                if (categories.get(position).equals("12")) {
                    MAX_CHAR = 12;
                    Toast.makeText(HomeActivity.this, "MAX_CHAR: "+MAX_CHAR, Toast.LENGTH_SHORT).show();
                }
                if (categories.get(position).equals("16")) {
                    MAX_CHAR = 16;
                    Toast.makeText(HomeActivity.this, "MAX_CHAR: "+MAX_CHAR, Toast.LENGTH_SHORT).show();
                }
                if (categories.get(position).equals("18")) {
                    MAX_CHAR = 18;
                    Toast.makeText(HomeActivity.this, "MAX_CHAR: "+MAX_CHAR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        passGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((capitalLetter.isChecked() || lowercaseLetter.isChecked() || numbers.isChecked() || symbols.isChecked())
                       ) {
                    gerateLePassword();
                }else {
                    showMessage("Please Check atleast one Checkbox");
                }


            }
        });




    }

    private void gerateLePassword() {
        final StringBuilder password = new StringBuilder();
        ArrayList<Integer> passSel = new ArrayList<Integer>();

        // MAX_CHAR =10;

        // when  UPPER CASE selected
        if (capitalLetter.isChecked())
            passSel.add(1);

        // when  LOWER CASE selected
        if (lowercaseLetter.isChecked())
            passSel.add(3);

        // when  Number  selected
        if (numbers.isChecked())
            passSel.add(0);

        // when  Special selected
        if (symbols.isChecked())
            passSel.add(2);

        else {
            showMessage("Please Check atleast one box");
        }

        for (int i = 1; i <= MAX_CHAR; ) {

            if (passSel.contains(0) && i <= MAX_CHAR) {
                password.append(getRandomPasswordCharacters(0));
                i++;
            }
            if (passSel.contains(1) && i <= MAX_CHAR) {
                password.append(getRandomPasswordCharacters(1));
                i++;
            }
            if (passSel.contains(2) && i <= MAX_CHAR) {
                password.append(getRandomPasswordCharacters(2));
                i++;
            }
            if (passSel.contains(3) && i <= MAX_CHAR) {
                password.append(getRandomPasswordCharacters(3));
                i++;
            }
        }
        Log.e("Your password is: ", password + "");
        passwordDisplay.setText(password);
        linearCopy.setVisibility(View.VISIBLE);
        CopyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipper(password.toString(),v);
            }
        });

        passClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("", "");
                clipboardManager.setPrimaryClip(clipData);

                passwordDisplay.setText("");
                Toast.makeText(getApplicationContext(), "Clipboard data has been cleared.", Toast.LENGTH_LONG).show();

            }
        });




    }

    private void copyToClipper(String generatedPassword,View v) {
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", generatedPassword);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);
        // Popup a snackbar.
//        Snackbar snackbar = Snackbar.make(v, generatedPassword+" Password: text has been copied to system clipboard.", Snackbar.LENGTH_LONG);
//        snackbar.show();
        showSpinner(generatedPassword +" "+" has been copied to system clipboard.");


    }

    private static String getRandomPasswordCharacters(int pos) {
        Random randomNum = new Random();
        StringBuilder randomChar = new StringBuilder();
        switch (pos) {
            case 0:
                randomChar.append(NUMBERS.charAt(randomNum.nextInt(NUMBERS.length() - 1)));
                break;
            case 1:
                randomChar.append(UPPER_ALPHABETS.charAt(randomNum.nextInt(UPPER_ALPHABETS.length() - 1)));
                break;
            case 2:
                randomChar.append(SPECIALCHARACTERS.charAt(randomNum.nextInt(SPECIALCHARACTERS.length() - 1)));
                break;
            case 3:
                randomChar.append(LOWER_ALPHABETS.charAt(randomNum.nextInt(LOWER_ALPHABETS.length() - 1)));
                break;
        }
        return randomChar.toString();

    }


    private void showMessage(String message){
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toasty.success(this, message, Toast.LENGTH_LONG, true).show();
    }
    private void showSpinner(String message){
        Snacky.builder()

                .setActivity(HomeActivity.this)
                .setMaxLines(4)
                .centerText()
                .setText(message)
                .setBackgroundColor(Color.parseColor("#00B873"))
                .setTextColor(Color.parseColor("#FFFFFF"))
//                .setActionText("Retry!")
//                .setActionTextColor(Color.parseColor("#00B873"))
//                .setActionClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        //firstLaunch();
//
//
//                    }
//                })
                .setActionTextSize(20)
                .setMaxLines(4)
                .centerText()

                .setActionTextTypefaceStyle(Typeface.BOLD)
                .setDuration(Snacky.LENGTH_LONG)
                .success()
                .show();

    }
}
