package demo.self.edu.itunesmusicsearch.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import demo.self.edu.itunesmusicsearch.R
import demo.self.edu.itunesmusicsearch.api.model.Track

class TrackAdapter(private val items: List<Track>?) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_result, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.tvItemNumber?.text = String.format("%3d", position + 1)

        val track = items?.get(position)
        holder.tvTrackTitle?.text = track?.trackName
        holder.tvTrackInfo?.text = track?.trackInfo
    }

    class TrackViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvItemNumber: TextView? = this.itemView?.findViewById(R.id.tvItemNumber)
        val tvTrackTitle: TextView? = this.itemView?.findViewById(R.id.tvTrackTitle)
        val tvTrackInfo: TextView? = this.itemView?.findViewById(R.id.tvTrackInfo)
    }
}