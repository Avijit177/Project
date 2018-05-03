package lazycoder.example.com.projecttesting;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import lazycoder.example.com.projecttesting.data.DataLoadingSubject;


public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    // The minimum number of items remaining before we should loading more.
    private static final int VISIBLE_THRESHOLD = 5;

    private final LinearLayoutManager layoutManager;
    private final DataLoadingSubject dataLoading;
    private final Runnable loadMoreRunnable = new Runnable() {
        @Override
        public void run() {
            onLoadMore();
        }
    };

    public InfiniteScrollListener(@NonNull LinearLayoutManager layoutManager,
                                  @NonNull DataLoadingSubject dataLoading) {
        this.layoutManager = layoutManager;
        this.dataLoading = dataLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || dataLoading.isDataLoading()) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();
        final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            recyclerView.post(loadMoreRunnable);
           
        }
            }

                public abstract void onLoadMore();

            }