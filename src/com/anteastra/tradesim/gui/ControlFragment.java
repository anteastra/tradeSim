package com.anteastra.tradesim.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ToggleButton;

import com.anteastra.tradesim.GameStateSingleton;
import com.anteastra.tradesim.InitialGameStateSingleton;
import com.anteastra.tradesim.R;
import com.anteastra.tradesim.activitylisteners.OnMoneyChangeListener;

public class ControlFragment extends Fragment{
	
	private View rootView;
	
	private OnMoneyChangeListener activitylistener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(InitialGameStateSingleton.APP_TAG, "ControlFragment onCreate done");
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_control, container, false);
                
        updateViews();
        initButtons();
        
        Log.v(InitialGameStateSingleton.APP_TAG, "ControlFragment onCreateView done");        
        return rootView;
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnMoneyChangeListener) {
			activitylistener = (OnMoneyChangeListener) activity;
	    } else {
	    	throw new ClassCastException(activity.toString() + " must implement OnGoodsBuyListener interface");
	    }
	}

	@Override
	public void onDetach() {
		super.onDetach();
	    activitylistener = null;
	}
	
	private void initButtons() {
		((Button)rootView.findViewById(R.id.ButtonGoodsBuy)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameStateSingleton.getInstance().buyGoods();
				updateViews();
				activitylistener.onMoneyChange();
			}
		});
		((Button)rootView.findViewById(R.id.ButtonRetailBuy)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameStateSingleton.getInstance().buyRetail();
				updateViews();
				activitylistener.onMoneyChange();
			}
		});
		((Button)rootView.findViewById(R.id.ButtonStuffHire)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameStateSingleton.getInstance().hireStuff();
				updateViews();
				activitylistener.onMoneyChange();
			}
		});
	}

	public void updateViews(){
		
		GameStateSingleton state = GameStateSingleton.getInstance();
		if (rootView != null) {
			((TextView) rootView.findViewById(R.id.TextViewGoods)).setText(String.valueOf(state.getGoodsCount()));
			((TextView) rootView.findViewById(R.id.TextViewAvrPrice)).setText(String.valueOf(state.getAvrgPrice()));	
			((TextView) rootView.findViewById(R.id.TextViewRetailOutlets)).setText(String.valueOf(state.getRetailCount()));
			((TextView) rootView.findViewById(R.id.TextViewRetailOlRent)).setText(String.valueOf(state.getRetailRent()));
			((TextView) rootView.findViewById(R.id.textViewStuffCount)).setText(String.valueOf(state.getStuffCount() + "/" + state.getMaxStuffCount()));
			((TextView) rootView.findViewById(R.id.textViewStuffPayment)).setText(String.valueOf(state.getStuffPayment()*state.getStuffCount()));
			
			((ToggleButton) rootView.findViewById(R.id.toggleButtonSell)).setChecked(state.isCanSell);
			rootView.invalidate();
		}
	}
}