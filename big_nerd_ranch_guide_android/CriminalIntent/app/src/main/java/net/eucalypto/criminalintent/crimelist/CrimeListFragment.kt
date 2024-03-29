package net.eucalypto.criminalintent.crimelist

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.criminalintent.Crime
import net.eucalypto.criminalintent.R
import timber.log.Timber
import java.util.*

class CrimeListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                Timber.d("Got crimes ${crimes.size}")
                val adapter = CrimeAdapter(crimes)
                crimeRecyclerView.adapter = adapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                viewModel.addCrime(crime)
                navigateToCrimeDetailFragment(crime.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {
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
                    navigateToCrimeDetailFragment(crime.id)
                }
            }
        }
    }

    private fun navigateToCrimeDetailFragment(crimeId: UUID) {
        val action = CrimeListFragmentDirections
            .actionCrimeListFragmentToCrimeDetailFragment(crimeId)
        val navHostFragment =
            parentFragmentManager.findFragmentById(R.id.nav_host_fragment_container)!!
        val navController = navHostFragment.findNavController()
        navController.navigate(action)
    }
}