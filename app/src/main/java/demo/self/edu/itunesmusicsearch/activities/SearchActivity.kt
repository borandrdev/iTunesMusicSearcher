package demo.self.edu.itunesmusicsearch.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import demo.self.edu.itunesmusicsearch.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
    }

    private fun initView() {
        edSearch.setSelection(edSearch.text.length)

        imgSearch.setOnClickListener { onSearchButtonClicked() }
    }

    private fun onSearchButtonClicked() {
        showSearchResults()
    }

    private fun showSearchResults() {
        val intent = Intent(this, SearchResultsActivity::class.java)
        intent.putExtra(SearchResultsActivity.ARG_STR_SEARCH_QUERY, edSearch.text.toString())
        startActivity(intent)
    }
}