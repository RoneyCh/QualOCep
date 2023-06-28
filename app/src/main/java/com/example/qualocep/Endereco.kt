package com.example.qualocep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Endereco(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val complemento: String = ""
)
 {
    constructor() : this("", "", "", "", "")
}

