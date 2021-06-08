package ru.gusev.usdcursapplication.ui.curs_list

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.gusev.usdcursapplication.domain.CursListUseCase

class CursListPresenterImpl constructor(private val useCase: CursListUseCase) :
    CursListPresenter() {

    private var getCursDisposable: Disposable? = null
    private var isFirstDataSet: Boolean = false

    override fun getCursInfo(isSwipeRefresh: Boolean) {
        useCase
            .getCurrentUsdCurs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoadingContent(true, !isSwipeRefresh)
            }
            .doFinally {
                viewState.showLoadingContent(false, !isSwipeRefresh)
            }
            .subscribe({
                viewState.setData(it)
                isFirstDataSet = true
            }, {
              viewState.showErrorGetCursList(!isSwipeRefresh)
            })
            .also {
                getCursDisposable = it
            }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCursInfo(false)
    }

    override fun detachView(view: CursListView?) {
        super.detachView(view)
        getCursDisposable?.dispose()
        getCursDisposable = null
        viewState.showLoadingContent(false, !isFirstDataSet)
        if(!isFirstDataSet) {
            viewState.showErrorGetCursList(true)
        }
    }
}