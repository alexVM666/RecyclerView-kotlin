package com.example.myreciclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main_recycler.*

class MainActivityRecycler : AppCompatActivity() {
    private lateinit var viewAdapter: EstudianteAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    val estudianteList: List<estudiante> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_recycler)

        viewManager = LinearLayoutManager(this)
        viewAdapter = EstudianteAdapter(estudianteList, this, { estud: estudiante -> onItemClickListener(estud) })

        rv_estudiante_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivityRecycler, DividerItemDecoration.VERTICAL))
        }

        // Metodo para implementar la eliminación de un estudiante, cuando el ususario da un onswiped en
        // el recyclerview
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val estud= viewAdapter.getTasks()
                val admin = adminBD(baseContext)
                if (admin.Ejecuta("DELETE FROM Estudiante WHERE noControl=" + estud[position].noCtrl) == 1){
                    retrieveEstudiantes()
                }
            }
        }).attachToRecyclerView(rv_estudiante_list)
    }

    //Evento clic cuando damos clic en un elemento del Recyclerview
    private fun onItemClickListener(Estud: estudiante) {
        Toast.makeText(this, "Clicked item" + Estud.nomEst, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        retrieveEstudiantes()
    }

    private fun retrieveEstudiantes() {
        val estudiantex = getEstudiantes()
        viewAdapter.setTask(estudiantex!!)
    }

    fun getEstudiantes(): MutableList<estudiante>{
        var estudiantes:MutableList<estudiante> = ArrayList()
        val admin = adminBD(this)

        //                                          0       1       2      3
        val tupla = admin.Consulta("SELECT noControl,nomEst,carrera,edadEst FROM Estudiante ORDER BY nomEst")
        while (tupla!!.moveToNext()) {
            val no = tupla.getString(0)
            val nom = tupla.getString(1)
            val car = tupla.getString(2)
            val eda = tupla.getInt(3)

            estudiantes.add(estudiante(no,nom,car,eda))
        }
        tupla.close()
        admin.close()
        return estudiantes
    }
}

