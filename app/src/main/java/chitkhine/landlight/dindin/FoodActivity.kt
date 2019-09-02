package chitkhine.landlight.dindin

import SharedPreference
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_food.*
import android.content.DialogInterface
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout


class FoodActivity : AppCompatActivity() {
    private var foodList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        val sf= SharedPreference (this)
        foodList = sf.getValueStringArray("foodList")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList)

        val textInputLayout = TextInputLayout(this)
        textInputLayout.setPadding(
            resources.getDimensionPixelOffset(R.dimen.dp_19), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
            0,
            resources.getDimensionPixelOffset(R.dimen.dp_19),
            0
        )
        val input = EditText(this)
        textInputLayout.hint = "My New Din Din"
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(this)
            .setTitle("Add Food")
            .setView(textInputLayout)
            .setMessage("Please enter the food you love")
            .setPositiveButton("Add") { dialog, i ->
                // do some thing with input.text
                val inputFood = input.text.toString().trim()
                input.text.clear()
                addFood(inputFood, dialog, i)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        foodListView.adapter = adapter

        foodListFab.setOnClickListener { view ->
            alert.show()
        }
    }

    private fun addFood(food: String, dialog: DialogInterface, intent: Int) {
        if (food.isNullOrEmpty()) {
            val toast = Toast.makeText(applicationContext, "Food cannot be empty", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            foodList.add(food)
            val sf= SharedPreference (this)
            sf.save("foodList", foodList)
        }
    }


}
