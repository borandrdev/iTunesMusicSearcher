package demo.self.edu.itunesmusicsearch.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import demo.self.edu.itunesmusicsearch.App
import demo.self.edu.itunesmusicsearch.R
import demo.self.edu.itunesmusicsearch.mvi.models.SearchScreenModel
import demo.self.edu.itunesmusicsearch.mvi.presenters.SearchPresenter
import demo.self.edu.itunesmusicsearch.mvi.views.SearchMvpView
import kotlinx.android.synthetic.main.activity_search.*
import ru.terrakok.cicerone.android.SupportAppNavigator

class SearchActivity :
        MvpAppCompatActivity(),
        SearchMvpView {

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        val appComponent = (application as App).diComponents.appComponent
        return SearchPresenter(appComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        (application as App).navigatorHolder.setNavigator(SearchActivityNavigator(this))
    }


    override fun onPause() {
        super.onPause()
        (application as App).navigatorHolder.removeNavigator()
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


// SearchMvpView =======================================================================================

    override fun render(model: SearchScreenModel) {
        imgSearch.visibility = if (model.isTextEntered) View.VISIBLE else View.GONE
    }


// Navigator =======================================================================================

    private class SearchActivityNavigator(val activity: FragmentActivity) : SupportAppNavigator(activity, 0) {
        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent {
            if ((screenKey == SearchPresenter.SEARCH_RESULTS_SCREEN) && (data is String)) {
                return createIntentToOpenSearchResultsForQuery(data)
            }
            return Intent()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment = Fragment()

        private fun createIntentToOpenSearchResultsForQuery(text: String): Intent {
            val intent = Intent(activity, SearchResultsActivity::class.java)
            intent.putExtra(SearchResultsActivity.ARG_STR_SEARCH_QUERY, text)
            return intent
        }
    }
}