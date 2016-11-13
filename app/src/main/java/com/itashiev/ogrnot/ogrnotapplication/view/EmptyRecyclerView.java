package com.itashiev.ogrnot.ogrnotapplication.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmptyRecyclerView extends RecyclerView {

    private View emptyView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            isEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            isEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            isEmptyView();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void isEmptyView() {
        if (emptyView != null && getAdapter() != null) {
            boolean isEmpty = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(isEmpty ? VISIBLE : GONE);
            setVisibility(isEmpty ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        isEmptyView();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        isEmptyView();
    }
}
