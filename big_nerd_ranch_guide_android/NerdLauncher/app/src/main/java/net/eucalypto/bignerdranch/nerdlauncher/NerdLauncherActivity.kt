package net.eucalypto.bignerdranch.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.bignerdranch.nerdlauncher.databinding.ActivityNerdLauncherBinding
import timber.log.Timber

class NerdLauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNerdLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNerdLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appRecyclerView.layoutManager =
            LinearLayoutManager(this)

        setUpAdapter()
    }

    private fun setUpAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities =
            packageManager.queryIntentActivities(startupIntent, 0)

        activities.sortWith { a, b ->
            String.CASE_INSENSITIVE_ORDER.compare(
                a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString()
            )
        }

        Timber.d("Found ${activities.size} activities")
        binding.appRecyclerView.adapter = ActivityAdapter(activities)
    }


    private class ActivityAdapter(val activities: List<ResolveInfo>) :
        RecyclerView.Adapter<ActivityAdapter.ActivityHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ActivityHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
            return ActivityHolder(view)
        }

        override fun onBindViewHolder(
            holder: ActivityHolder,
            position: Int
        ) {
            holder.bindActivity(activities[position])
        }

        override fun getItemCount(): Int {
            return activities.size
        }

        inner class ActivityHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            private val nameTextView = itemView as TextView
            private lateinit var resolveInfo: ResolveInfo

            fun bindActivity(resolveInfo: ResolveInfo) {
                this.resolveInfo = resolveInfo
                val packageManager = itemView.context.packageManager
                val appName =
                    resolveInfo.loadLabel(packageManager).toString()
                nameTextView.text = appName

                nameTextView.setOnClickListener {
                    val packageName =
                        resolveInfo.activityInfo.packageName
                    val className = resolveInfo.activityInfo.name
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        setClassName(packageName, className)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    it.context.startActivity(intent)
                }
            }

        }
    }
}