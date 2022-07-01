package cl.desafiolatam.desafiounobase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cl.desafiolatam.desafiounobase.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: String

    private lateinit var setUsers: HashSet<String>
    private lateinit var newUsers: HashSet<String>



    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(fileNameSharedPreferences, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setUsers = sharedPreferences.getStringSet(claveSetUsers , hashSetOf()) as HashSet<String>

        newUsers = hashSetOf()

        Toast.makeText(this,
            "${sharedPreferences.getStringSet(claveSetUsers, mutableSetOf())}", Toast.LENGTH_SHORT).show()
            // setOf("felipe")
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding){
            loginButton.setOnClickListener {

                if (nameInput.text!!.isNotEmpty()) {
                    user = nameInput.text.toString()

                    val intent: Intent
                    if (hasSeenWelcome()) {
                        intent = Intent(this@MainActivity, HomeActivity::class.java)
                    } else {
                        intent = Intent(this@MainActivity, WelcomeActivity::class.java)

                    }
                    intent.putExtra(userName, user)
                    startActivity(intent)

                } else {
                    Snackbar.make(container, "El nombre no puede estar vacío", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun hasSeenWelcome(): Boolean {
        var returnValue = false
        //implementar este método para saber si el usuario ya ha entrado a la aplicación y ha visto
        //la pantalla de bienvenida. Este método permite decidir que pantalla se muestra después de presionar Ingresar
        //recorra la lista de usuarios
        if(setUsers.contains(user)){
            returnValue = true
        } else {
            newUsers.add(user)
            newUsers.addAll(setUsers)
            sharedPreferences.edit().putStringSet(claveSetUsers, newUsers).apply()

        }

        return returnValue
    }

    override fun onResume() {
        super.onResume()

        setUsers = sharedPreferences.getStringSet(claveSetUsers, hashSetOf()) as HashSet<String>

    }

    companion object {

        const val fileNameSharedPreferences: String = "cl.desafiolatam.desafiounobase"

        const val claveSetUsers = "setUsers"
        const val userName = "username"

    }
}
