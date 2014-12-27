package com.anteastra.tradesim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class GameStateSingleton {

	private static Object syncObject = new Object();
	private static GameStateSingleton instance = null;
	private static Context context;
	private GameStateSingleton(){
		
	}
	
	public static GameStateSingleton getInstance(){
		if (instance == null){
			synchronized(syncObject){
				instance = new GameStateSingleton();
				instance.initialState();				
			}
		}		
		return instance;		
	}
	
	public int gameSpeed = InitialGameStateSingleton.gameSpeed;
	public final int SPEED_0 = InitialGameStateSingleton.SPEED_0;
	public final int SPEED_1 = InitialGameStateSingleton.SPEED_1;
	public final int SPEED_2 = InitialGameStateSingleton.SPEED_2;
	public final int SPEED_3 = InitialGameStateSingleton.SPEED_3;
	public final int DAY_DURATION = InitialGameStateSingleton.DAY_DURATION;
	public final int MAIN_TIMER_DURATION = InitialGameStateSingleton.MAIN_TIMER_DURATION;
	
	private int day = InitialGameStateSingleton.day;
	private int month = InitialGameStateSingleton.month;
	private Date gameDate = new Date();
	
	public int dayTimeLeft = InitialGameStateSingleton.dayTimeLeft;
	
	
	public int periodsCount = InitialGameStateSingleton.periodsCount;
	public int [] sellPrices = new int[periodsCount];
	public int [] buyPrices = new int[periodsCount];
	
	private int moneyAmount = InitialGameStateSingleton.moneyAmount;
	private int loadedSpace = InitialGameStateSingleton.loadedSpace;
	private int avaragePrice = InitialGameStateSingleton.avaragePrice;
	private int stuffCount = InitialGameStateSingleton.stuffCount;
	private int retailOutlets = InitialGameStateSingleton.retailOutlets;
	
	private int retailCost = InitialGameStateSingleton.retailCost;
	private int stuffPayment = InitialGameStateSingleton.stuffPayment;
	private int stuffHire = InitialGameStateSingleton.stuffHire;
	private int retailRent = InitialGameStateSingleton.retailRent;
	private int retailSpace = InitialGameStateSingleton.retailSpace;
	
	public boolean isCanSell = true;
	private Calendar calendarInstance = Calendar.getInstance();
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy");
	
	{
		calendarInstance.setTime(gameDate);
	}
	
	public static void setContext(Context con){
		context = con;
	}
	
	public void nextDay(){
		day++;
		calendarInstance.add(Calendar.DATE, 1);
		gameDate = calendarInstance.getTime();
		
		dayTimeLeft = DAY_DURATION;
		generatePrice();
		
		//count sellings
		if (isCanSell){
			Random rand = new Random();
			for(int i=0; i<stuffCount; i++){
				int canSell = rand.nextInt(10);
				int sells =0;
				if (canSell > loadedSpace){
					sells = loadedSpace;
				}else{
					sells = canSell;
				}
				moneyAmount += sells*getLastSellPrice();
				loadedSpace -= sells;
			}
		}
		
		if (calendarInstance.get(Calendar.DATE) == 1) {
			month++;
			moneyAmount = moneyAmount - retailOutlets * retailRent;
			moneyAmount = moneyAmount - stuffCount * stuffPayment;
			
			if (moneyAmount <0){
				//Intent myIntent = new Intent(context, LostActivity.class);
				//context.startActivity(myIntent);
			}
			
			if (moneyAmount > 100000){
				//Intent myIntent = new Intent(context, WinActivity.class);
				//context.startActivity(myIntent);
			}
		}
		
	}
	
	private void generatePrice(){
		Random rand = new Random();
		int sellPrice = rand.nextInt(50)+20;
		int buyPrice = sellPrice - (rand.nextInt(20)-5);
		
		for (int i=0; i<(periodsCount-1); i++){
			sellPrices[i]= sellPrices[i+1];
			buyPrices[i]= buyPrices[i+1];
		}
		sellPrices[periodsCount-1] = sellPrice;
		buyPrices[periodsCount-1] = buyPrice;
	}
	
	private void initialGeneratePrice(){	
		
		for (int i=0; i<periodsCount; i++){
			generatePrice();
		}		
	}
	
	private void initialState(){
		initialGeneratePrice();
	}
	
	public int getTodayBuyPrice(){
		return buyPrices[periodsCount-1];
	}
	public int getLastSellPrice(){
		return sellPrices[periodsCount-2];
	}
	
	public int getAvaliableSpace() {
		return retailSpace * retailOutlets - loadedSpace;
	}
	
	public static void reset(){
		instance = new GameStateSingleton();
	}
	
	public int getMoneyAmt() {
		return moneyAmount;
	}

	public void buyGoods() {
		int maxAvailableGoodsBySpace = getAvaliableSpace();
		int maxAvailableGoodsByCash = moneyAmount / getTodayBuyPrice();				
		int maxAvailableGoods = (maxAvailableGoodsByCash > maxAvailableGoodsBySpace)? maxAvailableGoodsBySpace: maxAvailableGoodsByCash;		
		
		
		if (maxAvailableGoods > 0) {
			int spendedCash = maxAvailableGoods * getTodayBuyPrice();
			moneyAmount = moneyAmount - spendedCash;
			int newLoadedSpace = loadedSpace + maxAvailableGoods;
			avaragePrice = (avaragePrice * loadedSpace + maxAvailableGoods * getTodayBuyPrice()) / newLoadedSpace;
			loadedSpace = newLoadedSpace;
		}
		
	}

	public void hireStuff() {
		if (moneyAmount >= stuffHire) {
			if (stuffCount < getMaxStuffCount()) {
				stuffCount++;
				moneyAmount -= stuffHire;
			}
		}
	}

	public void buyRetail() {
		if (moneyAmount >= retailCost) {
			retailOutlets++;
			moneyAmount -= retailCost;
		}
	}

	public int getGoodsCount() {
		return loadedSpace;
	}

	public int getAvrgPrice() {
		return avaragePrice;
	}

	public int getRetailCount() {
		return retailOutlets;
	}

	public int getRetailRent() {
		return retailRent;
	}

	public int getStuffCount() {
		return stuffCount;
	}
	
	public int getMaxStuffCount() {
		return retailOutlets * 2;
	}

	public int getStuffPayment() {
		return stuffPayment;
	}

	public String getCurrentDate() {
		return DATE_FORMAT.format(gameDate);
	}
}
