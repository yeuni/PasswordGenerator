package www.yeuni.com.passwordgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnHome;
    TextView txt_results;
    int length;
    private static int MAX_LENGTH;

    CheckBox capitalLetter, lowercaseLetter, numbers, symbols;
    RadioGroup passGroup;
    RadioButton sizeFour, sizeEight, sizeTwelve, sizeSixteen;
    EditText passHint;
    TextView passShow;
    Button passGenerate, passClear;
    int MAX_CHAR = 0;

    private static final String NUMBERS = "0123456789";
    private static final String UPPER_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIALCHARACTERS = "!@#$%^&*()_-+=<>?/{}~|";
  //  CheckBox chkCapital, chkSmal;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        length =10;
//
//
//        btnHome =findViewById(R.id.btnHome);
//        txt_results =findViewById(R.id.txt_results);
//
//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //generatePswd(length);
//            }
//        });
        capitalLetter = findViewById(R.id.capital_letter);
        lowercaseLetter = findViewById(R.id.lowercase_letter);
        numbers = findViewById(R.id.numbers);
        symbols = findViewById(R.id.symbols);

        passGroup = findViewById(R.id.passRadioGroup);

        sizeFour = findViewById(R.id.size_four);
        sizeEight = findViewById(R.id.size_eight);
        sizeTwelve = findViewById(R.id.size_twelve);
        sizeSixteen = findViewById(R.id.size_sixteen);

        passHint =  findViewById(R.id.passwordHint);

        passShow =  findViewById(R.id.passwordDisplay);

        passGenerate =  findViewById(R.id.passwordGenerate);
        passClear =  findViewById(R.id.passwordClear);

        String allowedCharacters = "";

        // Determines the types of characters permitted when a check box is checked.
        if (capitalLetter.isChecked()) {
            allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (lowercaseLetter.isChecked()) {
            allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
        }
        if (numbers.isChecked()) {
            allowedCharacters += "0123456789";
        }
        if (symbols.isChecked()) {
            allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
        }

        //Determines the length of the string based on which radio button the user has selected.
        int checkedRadioButtonId = passGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == 1) {
            MAX_LENGTH = 4;
        }
        if (checkedRadioButtonId == 2) {
            MAX_LENGTH = 8;
        }
        if (checkedRadioButtonId == 3) {
            MAX_LENGTH = 12;
        }
        if (checkedRadioButtonId == 4) {
            MAX_LENGTH = 16;
        }
        passGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder password = new StringBuilder();
                ArrayList<Integer> passSel = new ArrayList<Integer>();
                int selectedId = passGroup.getCheckedRadioButtonId();

                sizeFour = (RadioButton) findViewById(selectedId);

               // MAX_CHAR = Integer.parseInt(sizeFour.getText().toString());
                MAX_CHAR =10;

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
                passShow.setText("Your Password is: "+password +"");


        }
        });


    }

    public void ClickedCLear(View view) {
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
    }

//   private  char [] generatePswd(int len){
//       // txt_results.setText("Your Password:");
//        String charCaps ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String chars="abcdefghijklmnopqrstuvwxyz";
//        String nums="0123456789";
//        String symbols="!@#$%^&*(_+|><[),?=.";
//        String passSymbols = charCaps + chars +nums + symbols;
//        Random rnd = new Random();
//        char[] password = new char[len];
//        int index = 0;
//        for (int i = 0; i <len; i++){
//            password[i]=passSymbols.charAt(rnd.nextInt(passSymbols.length()));
//
//        }
//
//        Log.d("lerunguuuuu","password: "+password);
//       txt_results.setText("Password is : "+password);
//
//        return password;
//
//    }




}
