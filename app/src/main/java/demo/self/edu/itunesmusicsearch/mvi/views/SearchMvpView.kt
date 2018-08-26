package demo.self.edu.itunesmusicsearch.mvi.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface SearchMvpView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun startMusicSearchingForText(text: String)
}