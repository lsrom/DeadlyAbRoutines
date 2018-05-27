package cz.lsrom.deadlyabroutine.model

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.lsrom.deadlyabroutine.IRoutineSelector
import cz.lsrom.deadlyabroutine.R
import cz.lsrom.deadlyabroutine.RoutineActivity
import kotlinx.android.synthetic.main.card_routine.view.*

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class RoutineAdapter(
    val routines: List<Routine>,
    val listener: IRoutineSelector,
    val context: Context
) :
    RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val v = inflater.inflate(R.layout.card_routine, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.txtTitle?.text = routines.get(position).title
        holder?.itemView?.txtSummary?.text = routines.get(position).summary
        holder?.itemView?.txtFinished?.text = String.format(
            context.getString(R.string.general_routine_finished),
            routines.get(position).finished
        )

        val min = routines.get(position).averageTime / 60
        val sec = routines.get(position).averageTime % 60
        holder?.itemView?.txtAverageTime?.text =
                String.format(context.getString(R.string.general_routine_average_time), min, sec)

        holder?.itemView?.setOnClickListener(View.OnClickListener {
            listener.routineSelected(position)
        })

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}