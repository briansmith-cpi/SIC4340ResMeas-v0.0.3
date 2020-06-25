package com.sic.resmeasure.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sic.resmeasure.R;
import com.sic.resmeasure.binders.contacts.ChipSetupFragmentContact;
import com.sic.resmeasure.binders.models.ChipSetupFragmentViewModel;
import com.sic.resmeasure.binders.presenters.ChipSetupFragmentPresenter;
import com.sic.resmeasure.daos.LogItemDao;
import com.sic.resmeasure.daos.RegisterItemDao;
import com.sic.resmeasure.databinding.FragmentChipSetupBinding;
import com.sic.resmeasure.managers.IdrvManager;
import com.sic.resmeasure.utils.ResultRx;

import java.util.Locale;

import io.reactivex.functions.Consumer;

/**
 * @author ztrix
 * @version 1.0.0
 * @since 31-Jan-16
 */
public class ChipSetupFragment extends Fragment implements ChipSetupFragmentContact.View {

    private FragmentChipSetupBinding binding;

    public static ChipSetupFragment newInstance() {
        Bundle args = new Bundle();
        ChipSetupFragment fragment = new ChipSetupFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chip_setup, container, false);
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
        binding.setModel(new ChipSetupFragmentViewModel());
        binding.setPresenter(new ChipSetupFragmentPresenter(this));
    }

    public LogItemDao getLogData() {
        ChipSetupFragmentViewModel model = binding.getModel();
        LogItemDao logItemDao = new LogItemDao();

        logItemDao.setiBias1(String.format(Locale.US, "%d", model.biasCurrent1.get()));
        logItemDao.setRange(1800);
        return logItemDao;
    }

    public void putRegisterData(RegisterItemDao register) {
        ChipSetupFragmentViewModel model = binding.getModel();

        byte idrv1 = (byte) Math.round(model.biasCurrent1.get() / model.getAdjustBias1());
        register.setIdrvRange1((byte) (model.biasCurrent1.get() > 63 ? 1 : 0));
        register.setIdrvValue1(idrv1);
        register.setDac1Value((byte) 0x00);
        register.setChannel((byte) model.channelPosition.get());
//        Timber.wtf("------> %s", model.channelPosition.get());
    }

    @Override
    public void samplingFrequencyChange(ChipSetupFragmentViewModel model) {

    }

    @Override
    public void clearLogClicked(ChipSetupFragmentViewModel model) {
        model.adapter.get().clear();
    }

    public Consumer<ResultRx> getSubscribeGetAdc(LogItemDao dao) {
        return result -> {
//            Timber.d("Subscribe %d", result.getState());
            if (result.getState() > 0) {
                int raw = result.getAdcResult();
                float vout;
                float r;
                String biasString;

                vout = IdrvManager.getInstance().getAdcResultWithBuffer(raw);

                if (vout < 0){
                    vout = 0;
                }
                dao.setVout(String.format(Locale.US, "%.4f", vout));
                dao.setAdcResult(String.format(Locale.US, "0x%04X", raw));

                binding.getModel().adapter.get().addData(dao);
                binding.getModel().adapter.get().notifyDataSetChanged();
                binding.rcvLog.smoothScrollToPosition(0);

                biasString = dao.getiBias1();

                binding.getModel().buffer.set(true);
                binding.getModel().range.set(dao.getRange());
                binding.getModel().bias.set(Integer.parseInt(biasString));
                binding.getModel().value.set(vout * 1000f);
                dao.setiBias(biasString);

                r = vout / (float)(Integer.parseInt(biasString) * 0.000001);
                if (r < 0){
                    r = 0;
                }
                dao.setOhm(String.format(Locale.US, "%.2f", r));
//                Log.wtf("---- R = ", String.valueOf(r));
//                Log.wtf("---- vout = ", String.valueOf(vout));
//                Log.wtf("---- raw = ", String.valueOf(raw));
            }
        };
    }
}
