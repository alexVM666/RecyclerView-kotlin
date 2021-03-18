package com.example.myreciclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.celda_prototipo_estudiante.view.*

class EstudianteAdapter(private var mListaEstudiantes:List<estudiante>,
                        private val mContext: Context,
                        private val clickListener: (estudiante) -> Unit)
    : RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {


    /**
     * onCreateViewHolder() que como su nombre indica lo que hará será devolvernos
     * un objeto ViewHolder al cual le pasamos la celda prototió que hemos creado.
     *
     * @return Un nuevo EstudianteViewHolder que contiene la vista para cada estudiante
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        return EstudianteViewHolder(layoutInflater.inflate(R.layout.celda_prototipo_estudiante, parent, false))
    }

    /**
     * La clase RecyclerView. onBindViewHolder() se encarga de coger cada una de las
     * posiciones de la lista de estudiantes y pasarlas a la clase ViewHolder(
     *
     * @param holder   Vincular los datos del cursor al ViewHolder
     * @param position La posición de los datos en la lista
     */
    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        holder.bind(mListaEstudiantes[position], mContext, clickListener)
    }

    /**
     * El método getItemCount() nos devuelve el tamaño de la lista, que lo necesita
     * el RecyclerView.
     */
    override fun getItemCount(): Int = mListaEstudiantes.size

    /**
     * Cuando los datos cambian, este metodo actualiza la lista de estudiantes
     * y notifica al adaptador a usar estos nuevos valores
     */
    fun setTask(estudiantes: List<estudiante>){
        mListaEstudiantes = estudiantes
        notifyDataSetChanged()
    }

    fun getTasks(): List<estudiante> = mListaEstudiantes

    /**
     * Clase interna para crear ViewHolders
     */
    class EstudianteViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView) {

        fun bind (estud:estudiante, context: Context, clickListener: (estudiante) -> Unit){
            //Asigna los valores a los elementos del la celda_prototipo_estudiante
            itemView.tvnomEst.text = estud.nomEst.toString()
            itemView.tvCarrera.text = estud.carrera.toString()

            itemView.setOnClickListener{ clickListener(estud)}
        }
    }

}
