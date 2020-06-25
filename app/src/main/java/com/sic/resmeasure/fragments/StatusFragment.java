package com.sic.resmeasure.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.sic.resmeasure.R;
import com.sic.resmeasure.activities.MainActivity;
import com.sic.resmeasure.binders.contacts.StatusFragmentContact;
import com.sic.resmeasure.binders.models.StatusFragmentViewModel;
import com.sic.resmeasure.binders.presenters.StatusFragmentPresenter;
import com.sic.resmeasure.databinding.FragmentStatusBinding;
import com.sic.resmeasure.utils.ResultRx;
import com.sic.resmeasure.utils.Toaster;

import io.reactivex.functions.Consumer;


/**
 * @author ztrix
 * @version 1.0.0
 * @since 31-Jan-16
 */
public class StatusFragment extends Fragment implements StatusFragmentContact.View {
    private FragmentStatusBinding binding;

    public static StatusFragment newInstance() {
        Bundle args = new Bundle();
        StatusFragment fragment = new StatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_status, container, false);
        initInstances();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    private void init() {
    }

    private void initInstances() {
        StatusFragmentViewModel model = new StatusFragmentViewModel();
        StatusFragmentPresenter presenter = new StatusFragmentPresenter(this);
        binding.setModel(model);
        binding.setPresenter(presenter);
        setStatus("Please tap a Tag", R.color.blue_500);
    }

    private void setStatus(String text, int resId) {
        binding.getModel().text.set(text);
        binding.getModel().textColor.set(getResources().getColor(resId));
    }

    public Consumer<ResultRx> getSubscribeInitial() {
        return result -> {
//            Timber.d("Subscribe %d", result.getState());
            switch (result.getState()) {
                case -2:
                case -3:
                case -4:
//                    setStatus("Power is not enough", R.color.orange_500);
//                    break;{
                    Toaster.showToast(getContext(), "Tag was lost");
                    setStatus("Tag was lost", R.color.orange_500);
                    break;
                case -1:
                    Toaster.showToast(getContext(), "Tag is not supported");
                    setStatus("-Tag is not supported", R.color.red_500);
                    break;

                default:
                    Toaster.showToast(getContext(), "SIC4340 detected");
                    setStatus("SIC4340 detected", R.color.green_500);
                    break;
            }
        };
    }

    public Consumer<ResultRx> getSubscribeGetAdc() {
        return result -> {
//            Timber.d("Subscribe %d", result.getState());
            switch (result.getState()) {
                case -2:
                case -7:
                    setStatus("Power is not enough", R.color.orange_500);
                    break;
                case -3:
                case -4:
                case -6:
                    setStatus("Tag was lost", R.color.orange_500);
                    break;
                case -1:
                    setStatus("Tag is not supported", R.color.red_500);
                    break;

                case -5:
                default:
                    setStatus("Transmission complete", R.color.green_500);
                    break;
            }
        };
    }

    @Override
    public void sendClicked(StatusFragmentViewModel model) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);

        Intent intent = new Intent(MainActivity.ACTION_CALL_ON_CLICK);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    public void setPosition(int position) {
        binding.getModel().page.set(position);
    }
}
