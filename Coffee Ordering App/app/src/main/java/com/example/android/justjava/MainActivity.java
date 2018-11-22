

package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberofCoffees = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    private int calculatePrice(int quantity, int price) {

        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean b= whippedCreamBox.isChecked();

        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();

        int total = quantity*price;

        if(b == true)
            total = total + 2*quantity;

        if(hasChocolate == true)
            total = total + quantity;

        return total;
    }

    public void incrementOrder(View view) {
        numberofCoffees = numberofCoffees + 1;
        display(numberofCoffees);
    }

    public void decrementOrder(View view) {
        numberofCoffees = numberofCoffees - 1;
        if (numberofCoffees < 0)
            numberofCoffees = 0;
        display(numberofCoffees);
    }

    private String createOrderSummary(int price) {

        EditText nameText = (EditText) findViewById(R.id.name_editText);
        String name = nameText.getText().toString();

        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean b= whippedCreamBox.isChecked();
        String temp = "Name: " + name +"\nQuantity: " + numberofCoffees;

        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();

        if(b == true && numberofCoffees > 0)
            temp = temp + "\nWhipped Cream: True";
        else
            temp = temp + "\nWhipped Cream: False";

        if(hasChocolate == true && numberofCoffees > 0)
            temp = temp + "\nChocolate: True";
        else
            temp = temp + "\nChocolate: False";

        temp = temp +  "\nTotal: $" + price + "\nThank You!!!";
        return temp;

    }

    public String createSubject() {
        EditText nameText = (EditText) findViewById(R.id.name_editText);
        String name = nameText.getText().toString();
        String subject = "Coffee Order for " + name;
        return subject;
    }

    public void submitOrder(View view) {
        /*displayPrice(numberofCoffees * 5);*/
        int price = calculatePrice(numberofCoffees, 5);
        composeEmail(createSubject(), createOrderSummary(price));
    }

    public void composeEmail(String subject, String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: KwK@KWK.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}