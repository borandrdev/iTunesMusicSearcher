package demo.self.edu.itunesmusicsearch.mvi.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import demo.self.edu.itunesmusicsearch.mvi.models.SearchResultsScreenModel

interface SearchResultsView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun render(model: SearchResultsScreenModel)
}