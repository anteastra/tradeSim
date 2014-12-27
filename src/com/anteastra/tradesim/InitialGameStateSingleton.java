package com.anteastra.tradesim;

public class InitialGameStateSingleton {
	
	public static String APP_TAG = "TradeSim";
	
	public static int gameSpeed = 0;
	public static final int SPEED_0 = 0;
	public static final int SPEED_1 = 25;
	public static final int SPEED_2 = 50;
	public static final int SPEED_3 = 100;
	public static final int DAY_DURATION = 1000;
	public static final int MAIN_TIMER_DURATION = 200;	
	
	public static int day = 0;
	public static int month = 0;
	public static int dayTimeLeft = DAY_DURATION;
	
	public static int periodsCount = 10;
	public static int moneyAmount = 500;	
	public static int loadedSpace = 150;
	public static int avaragePrice = 30;
	public static int stuffCount = 0;
	public static int retailOutlets = 1;
	
	public static int retailCost = 1500;
	public static int stuffPayment = 750;
	public static int stuffHire = 250;
	public static int retailRent = 1500;	
	public static int retailSpace = 200;
	
	public static boolean isCanSell = true;
	
}
