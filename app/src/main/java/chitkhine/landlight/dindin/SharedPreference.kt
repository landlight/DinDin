import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "FOODLIST_PREF"

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, status!!)

        editor.commit()
    }

    fun save(KEY_NAME: String, values: ArrayList<String>) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val a = JSONArray()
        println(values.size)
        println(values)
        for (i in 0..(values.size-1)) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            editor.putString(KEY_NAME, a.toString())
        } else {
            editor.putString(KEY_NAME, null)
        }
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun getValueStringArray(KEY_NAME: String): ArrayList<String> {
        val json = sharedPref.getString(KEY_NAME, null)
        var urls: ArrayList<String> = ArrayList<String>()
        if (!json.isNullOrEmpty()){
            val a = JSONArray(json)
            for (i in 0..a.length()-1) {
                var url = a.optString(i)
                urls.add(url)
            }
        }
        return urls
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_NAME)
        editor.commit()
    }
}