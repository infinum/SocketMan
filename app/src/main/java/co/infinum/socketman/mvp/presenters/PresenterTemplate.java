package co.infinum.socketman.mvp.presenters;

import co.infinum.socketman.mvp.views.BaseView;

/**
 * A template for a common Presenter in MVP model. Provides a
 * constructor that take View and Interactor instance.
 *
 * @param <V> view implementation that will be used with this presenter
 * @param <I> interactor implementation that will be used with this presenter
 *            Created by Željko Plesac on 05/04/16.
 */
public abstract class PresenterTemplate<V extends BaseView, I> {

    /**
     * View reference.
     */
    protected V view;

    /**
     * Interactor reference.
     */
    protected I interactor;

    /**
     * Constructor for injecting View and Interactor.
     *
     * @param view       view reference
     * @param interactor interactor reference
     */
    public PresenterTemplate(V view, I interactor) {
        this.view = view;
        this.interactor = interactor;
    }
}
