package com.example.xaocu.test.mvp;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public abstract class BaseMvpFragment<V extends BaseMVPView, P extends BaseMvpPresenter<V>> extends MvpFragment<V, P> {
}
