package cl.desafiolatam.desafiounobase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.desafiolatam.desafiounobase.MainActivity.Companion.userName

import cl.desafiolatam.desafiounobase.databinding.ActivityHomeBinding

import com.google.android.material.snackbar.Snackbar



class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user: String

    // para almacenar los datos en shared preferences utilice claves que contengan el nombre del usuario y el nombre de el campo guardado.
    //esta recomendación no aplica para todos los valores, pero ayuda con varios
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var userKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(MainActivity.fileNameSharedPreferences, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        user = sharedPreferences.getString(userName, "")!!



        loadData()
        binding.saveButton.setOnClickListener {
            //Agregar los pasos necesario para guardar los datos
            saveLanguages()

            saveNickNameAndAge()

            Snackbar.make(binding.container, "Datos guardados", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun loadData() {
        user = intent.getStringExtra(userName) ?: ""
        userKey = user.toUpperCase()
        val title = "BienvenidoEsta es la pantalla inicial, aquí $user podrá ver todas sus preferencias"
        binding.homeTitle.text = title
        handleLanguages()
        loadProfile()
    }

    private fun loadProfile() {
        //crear las claves para buscar y almacenar los datos, recuerde asociar las mismas al usuario de alguna manera

        val nickNameString = sharedPreferences.getString(userKey + nickNameKey, "")
        binding.nicknameInput.setText(nickNameString)
        binding.nicknameTitle.text = nickNameString
        val ageString = sharedPreferences.getInt(userKey + ageKey, 0)
        binding.ageInput.setText(ageString.toString())
    }

    private fun handleLanguages() {
        val languagesKey = ""
        //val languages = mutableSetOf()
        //ocupar resolveLanguage para cargar los datos iniciales en los checkboxs
        binding.languageOne.isChecked = sharedPreferences.getBoolean(userKey + spanishKey, false)
        binding.languageTwo.isChecked = sharedPreferences.getBoolean(userKey + englishKey, false)
        binding.languageThree.isChecked = sharedPreferences.getBoolean(userKey + germanKey, false)
        binding.languageOther.isChecked = sharedPreferences.getBoolean(userKey + otherKey, false)
        binding.otherLanguageInput.setText( sharedPreferences.getString(userKey + otherInputKey, ""))

    }


    private fun saveLanguages() {
        //crear lo necesario para guardar los idiomas seleccionados por el usuario
        //en sharedpreferences
//        val languagesKey = "francisco123"

        sharedPreferences.edit().putBoolean(userKey + spanishKey, binding.languageOne.isChecked).apply()
        sharedPreferences.edit().putBoolean(userKey + englishKey, binding.languageTwo.isChecked).apply()
        sharedPreferences.edit().putBoolean(userKey + germanKey, binding.languageThree.isChecked).apply()
        sharedPreferences.edit().putBoolean(userKey + otherKey, binding.languageOther.isChecked).apply()
        sharedPreferences.edit().putString(userKey + otherInputKey, binding.otherLanguageInput.text.toString()).apply()
    }

    private fun saveNickNameAndAge() {
//        val nicknameKey = ""
//        val ageKey = ""
        if (binding.nicknameInput.text!!.isNotEmpty()) {
           //almacenar
            sharedPreferences.edit().putString(userKey + nickNameKey, binding.nicknameInput.text.toString()).apply()
        }
        if (binding.ageInput.text!!.isNotEmpty()) {
            val ageInt = binding.ageInput.text.toString().toInt()
            //almacenar
            sharedPreferences.edit().putInt(userKey + ageKey, ageInt).apply()
        }
    }

    companion object{

        val spanishKey = "spanish"
        val englishKey = "english"
        val germanKey = "german"
        val otherKey = "otherLanguage"
        val otherInputKey = "otherInputLanguage"
        val nickNameKey =  "nickName"
        val ageKey =  "age"

    }
}
