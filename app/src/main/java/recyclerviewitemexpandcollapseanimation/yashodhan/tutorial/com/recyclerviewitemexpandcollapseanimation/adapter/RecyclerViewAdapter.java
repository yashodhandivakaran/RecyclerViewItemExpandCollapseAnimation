package recyclerviewitemexpandcollapseanimation.yashodhan.tutorial.com.recyclerviewitemexpandcollapseanimation.adapter;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import recyclerviewitemexpandcollapseanimation.yashodhan.tutorial.com.recyclerviewitemexpandcollapseanimation.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private View oldView;
    private int originalHeight = 0;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View container;
        public View bottomContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            this.container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(originalHeight == 0){
                originalHeight = v.getHeight();
            }
            bottomContainer.setVisibility(bottomContainer.getVisibility()  == View.VISIBLE ? View.GONE : View.VISIBLE);
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            int newHeight = v.getMeasuredHeight();
            animateTheClickAction(v,oldView,originalHeight,newHeight);
            oldView = v;
        }
    }

    private void animateTheClickAction(final View view, final View previousView, int originalHeight, int newHeight) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + (int) (originalHeight * 0.5));
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            }
        });


        ValueAnimator valueAnimator2 = ValueAnimator.ofInt(originalHeight + (int) (originalHeight * 0.5), originalHeight);
        valueAnimator2.setDuration(200);
        valueAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                previousView.getLayoutParams().height = value.intValue();
                previousView.requestLayout();
            }
        });


        if (previousView == null) {
            valueAnimator.start();
        } else {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(valueAnimator, valueAnimator2);
            set.start();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        vh.bottomContainer = v.findViewById(R.id.bottom_container);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
