package chitkhine.landlight.dindin

import SharedPreference
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var foodList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sf= SharedPreference (this)

        foodList = sf.getValueStringArray("foodList")
        if (foodList.count() == 0) {
            foodList.add("No Food Added Yet")
        }

        decideBtn.setOnClickListener {
            val random = Random()
            val randomFood = random.nextInt(foodList.count())
            selectedTxt.text = foodList[randomFood]
        }
        println("foodList $foodList")
        addFoodBtn.setOnClickListener {
            val newFood = addFoodTxt.text.toString()
            if (newFood.isNullOrBlank()){
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("You must add favourite food to increase your food decision")
                    .setCancelable(false)
                    // negative button text and action
                    .setNegativeButton("OK", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })
                val alert = dialogBuilder.create()
                alert.show()
            }else {
                if (foodList[0] == "No Food Added Yet"){
                    foodList.removeAt(0)
                }
                foodList.add(newFood.trim())
                sf.save("foodList", foodList)
                addFoodTxt.text.clear()
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "this is a test activity")
            }
            startActivity(intent)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
