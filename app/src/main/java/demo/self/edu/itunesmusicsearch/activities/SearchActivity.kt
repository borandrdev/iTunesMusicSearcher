package demo.self.edu.itunesmusicsearch.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import demo.self.edu.itunesmusicsearch.R
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchPresenter
import demo.self.edu.itunesmusicsearch.mvi.views.SearchMvpView
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : MvpAppCompatActivity(), SearchMvpView {

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter = SearchPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        initSearchInputView(savedInstanceState)

        imgSearch.setOnClickListener { presenter.onSearchButtonClicked() }
    }

    private fun initSearchInputView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            presenter.onSearchTextChanged(edSearch.text.toString())
        }
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onSearchTextChanged(p0.toString())
            }
        })
        edSearch.setSelection(edSearch.text.length)
        edSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onSearchButtonClicked()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun openSearchResultsActivityForText(text: String) {
        val intent = Intent(this, SearchResultsActivity::class.java)
        intent.putExtra(SearchResultsActivity.ARG_STR_SEARCH_QUERY, text)
        startActivity(intent)
    }


// SearchMvpView ===================================================================================
    override fun startMusicSearchingForText(text: String) {
        openSearchResultsActivityForText(text)
    }
}