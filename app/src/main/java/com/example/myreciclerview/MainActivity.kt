package com.example.myreciclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var no: String = ""
    var nom: String = ""
    var carr: String = ""
    var edad: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buscarEstudiante(v: View){
        if (etControl.text.isEmpty())
        {
            Toast.makeText(this,"Ingresa el número de control para Buscar", Toast.LENGTH_LONG).show()
        }
        else
        {
            no = etControl.text.toString()
            val admin = adminBD(this)
            //                                           0       1       2      3
            val tupla = admin.Consulta("select noControl,nomEst,carrera,edadEst from Estudiante where noControl='$no'")
            if (tupla!!.moveToFirst())
            {
                etNombre.setText(tupla.getString(1))
                etCarrera.setText(tupla.getString(2))
                etEdad.setText(tupla.getString(3))
                btnAgregar.isEnabled = false
                btnModificar.isEnabled=true
                btnEliminar.isEnabled=true
            }
            else
            {
                Toast.makeText(this, "No Existe el Numero de Control", Toast.LENGTH_SHORT).show();
                etControl.requestFocus()
            }

        }
    }

    fun agregarEstudiante(v: View){
        if (etControl.text.isEmpty() || etNombre.text.isEmpty() ||
            etCarrera.text.isEmpty() || etEdad.text.isEmpty())
        {
            Toast.makeText(this, "Falta información del Estudiante", Toast.LENGTH_SHORT).show();
            etControl.requestFocus()
        }
        else
        {
            leerCajas()
            val sentencia  = "INSERT INTO Estudiante(noControl,nomEst,carrera,edadEst) " +
                    " values ('$no','$nom','$carr',$edad)"
            val admin = adminBD(this)
            if (admin.Ejecuta(sentencia) == 1)
            {
                Toast.makeText(this, "Se Agrego Estudiante", Toast.LENGTH_SHORT).show();
                limpiarCajas(v)
            }
            else
            {
                Toast.makeText(this, "Estudiante NO AGREGADO", Toast.LENGTH_SHORT).show();
                etControl.requestFocus()
            }

        }
    }

    fun actualizarEstudiante(v: View){
        if (etControl.text.isEmpty() || etNombre.text.isEmpty() ||
            etCarrera.text.isEmpty() || etEdad.text.isEmpty())
        {
            Toast.makeText(this, "Falta información del Estudiante", Toast.LENGTH_SHORT).show();
            etControl.requestFocus()
        }
        else
        {
            leerCajas()
            val sentencia  = "UPDATE Estudiante SET nomEst='$nom',carrera='$carr',edadEst=$edad WHERE noControl='$no'"

            val admin = adminBD(this)
            if (admin.Ejecuta(sentencia) == 1)
            {
                Toast.makeText(this, "Se Modifico el Estudiante", Toast.LENGTH_SHORT).show();
                limpiarCajas(v)
            }
            else
            {
                Toast.makeText(this, "El Estudiante NO SE MODIFICO", Toast.LENGTH_SHORT).show();
                etControl.requestFocus()
            }

        }
    }

    fun eliminarEstudiante(v: View){
        if (etControl.text.isEmpty() || etNombre.text.isEmpty() ||
            etCarrera.text.isEmpty() || etEdad.text.isEmpty())
        {
            Toast.makeText(this, "Falta información del Estudiante", Toast.LENGTH_SHORT).show();
            etControl.requestFocus()
        }
        else
        {
            leerCajas()
            val sentencia  = "DELETE FROM Estudiante WHERE noControl='$no'"
            val admin = adminBD(this)
            if (admin.Ejecuta(sentencia) == 1)
            {
                Toast.makeText(this, "Se ELIMINO el Estudiante", Toast.LENGTH_SHORT).show();
                limpiarCajas(v)
            }
            else
            {
                Toast.makeText(this, "El Estudiante NO ELIMINADO", Toast.LENGTH_SHORT).show();
                etControl.requestFocus()
            }

        }
    }

    fun leerCajas()
    {
        no = etControl.text.toString()
        nom = etNombre.text.toString()
        carr = etCarrera.text.toString()
        edad = etEdad.text.toString()
    }

    fun limpiarCajas(v:View)
    {
        no=""
        nom=""
        carr=""
        edad="0"
        etControl.setText("")
        etNombre.setText("")
        etCarrera.setText("")
        etEdad.setText("")
        btnAgregar.isEnabled = true
        btnModificar.isEnabled = false
        btnEliminar.isEnabled = false
        etControl.requestFocus()
    }

    fun Consultar(v: View){
        var actividad = Intent(this,MainActivityRecycler::class.java)
        startActivity(actividad)
    }
}

