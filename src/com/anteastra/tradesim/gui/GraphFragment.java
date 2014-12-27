package com.anteastra.tradesim.gui;

import com.anteastra.tradesim.GameStateSingleton;
import com.anteastra.tradesim.InitialGameStateSingleton;
import com.anteastra.tradesim.MySurfaceView;
import com.anteastra.tradesim.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GraphFragment extends Fragment{
	
	private View rootView;
	private MySurfaceView surface;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(InitialGameStateSingleton.APP_TAG, "GraphFragment onCreate done");
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        
        SurfaceView surfaceViewMain = (SurfaceView)rootView.findViewById(R.id.surfaceViewMain);
		LinearLayout lay = (LinearLayout)rootView.findViewById(R.id.linearLayoutSurface);
		surface = new MySurfaceView(this.getActivity());
		ViewGroup.LayoutParams p = surfaceViewMain.getLayoutParams();
		surface.setLayoutParams(p); 
		lay.addView(surface);
		lay.removeView(surfaceViewMain);
        
        updateViews();
        
        Log.v(InitialGameStateSingleton.APP_TAG, "GraphFragment onCreateView done");
        
        return rootView;
    }
	
	public void updateViews(){
		
		GameStateSingleton state = GameStateSingleton.getInstance();
		if (rootView != null) {
			((TextView) rootView.findViewById(R.id.textViewBuyPrice)).setText(String.valueOf(state.buyPrices[state.periodsCount-1]));
			rootView.findViewById(R.id.textViewBuyPrice).invalidate();
			((TextView) rootView.findViewById(R.id.textViewSellPrice)).setText(String.valueOf(state.sellPrices[state.periodsCount-1]));
			rootView.findViewById(R.id.textViewBuyPrice).invalidate();
			((TextView) rootView.findViewById(R.id.textViewMoney)).setText(String.valueOf(state.getMoneyAmt()));
			rootView.findViewById(R.id.textViewBuyPrice).invalidate();
			((TextView) rootView.findViewById(R.id.textViewDay)).setText(String.valueOf(state.getCurrentDate()));
			rootView.findViewById(R.id.textViewBuyPrice).invalidate();
		}
	}
}
