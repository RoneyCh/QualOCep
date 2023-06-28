package com.example.qualocep

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity
import com.example.qualocep.ApiClient
import com.example.qualocep.Endereco
import com.example.qualocep.R


class MainActivity : AppCompatActivity() {
    private lateinit var editTextCep: EditText
    private lateinit var buttonSearch: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCep = findViewById(R.id.editTextCep)
        buttonSearch = findViewById(R.id.buttonSearch)
        textViewResult = findViewById(R.id.textViewResult)

        buttonSearch.setOnClickListener {
            val cep = editTextCep.text.toString()
            if (cep.isNotBlank()) {
                searchEndereco(cep)
            }
        }
    }

    private fun searchEndereco(cep: String) {
        Log.d(TAG, "CEP: $cep")
        val call = ApiClient.viaCepService.getEndereco(cep)

        call.enqueue(object : Callback<Endereco> {
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                if (response.isSuccessful) {
                    val Endereco = response.body()
                    Endereco?.let {
                        val result = "CEP: ${it.cep}\n" +
                                "Logradouro: ${it.logradouro}\n" +
                                "Bairro: ${it.bairro}\n" +
                                "Localidade: ${it.localidade}\n" +
                                "UF: ${it.uf}"
                        textViewResult.text = result
                    }
                } else {
                    textViewResult.text = "Erro na requisição"
                }
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                val errorBody = t.message
                Log.e(TAG, "Erro na requisição: $errorBody")
                textViewResult.text = "Falha na requisição"
            }

        })
    }
}
