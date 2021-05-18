package net.eucalypto.criminalintent.crimelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.criminalintent.Crime
import net.eucalypto.criminalintent.R

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CrimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
        val crimeRecyclerView: RecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = CrimeAdapter(emptyList())
        viewModel.crimeList.observe(viewLifecycleOwner) { crimes ->
            crimes?.let {
                Log.d(TAG, "Got crimes ${crimes.size}")
                val adapter = CrimeAdapter(crimes)
                crimeRecyclerView.adapter = adapter
            }
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        private lateinit var crime: Crime

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            holder.bind(crimes[position])
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

    }

}