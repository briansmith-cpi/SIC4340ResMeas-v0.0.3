package com.sic.resmeasure.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.sic.resmeasure.R;
import com.sic.resmeasure.binders.models.LogViewHolderModel;
import com.sic.resmeasure.daos.LogItemDao;
import com.sic.resmeasure.widgets.holders.LogViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztrix
 * @version 1.0.0
 * @since 21-Feb-16
 */
public class LogListAdapter extends RecyclerView.Adapter<LogViewHolder> {

    private List<LogItemDao> data = new ArrayList<>();
    private boolean refresh = false;

    public static LogListAdapter newInstance() {
        return new LogListAdapter();
    }

    public LogViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return LogViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    public void onBindViewHolder(LogViewHolder logViewHolder, int position) {
        LogItemDao logItemDao = data.get(position);
        LogViewHolderModel model = new LogViewHolderModel();
        model.vout.set(logItemDao.getVout());
        model.bias.set(logItemDao.getiBias());
        model.ohm.set(logItemDao.getOhm());

        logViewHolder.bindTo(model);
        logViewHolder.setBackgroundColor(position);

        if (refresh) {
            setAnimation(logViewHolder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    private void setAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.down_from_top);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                refresh = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
        refresh = true;
    }

    public void addData(LogItemDao dao) {
        data.add(0, dao);
        notifyItemInserted(0);
        refresh = true;
    }
}
