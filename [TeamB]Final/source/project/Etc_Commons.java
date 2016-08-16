package project;

import java.util.Calendar;

public class Etc_Commons {	
	//날짜 (년, 월, 일)
	public static Calendar calendar = Calendar.getInstance();	
	public static final int YEAR = calendar.get(Calendar.YEAR);	
	public static final int MONTH = calendar.get(Calendar.MONTH) + 1;	
	public static final int DAY = calendar.get(Calendar.DATE);
	
	//E-mail : 관리자 메일 계정
	public static final String EmailID = "acornassetmgr@gmail.com";	
	public static final String EmailPW = "acrongoodjob";
	
	//파일경로
	public static final String DB_PROPERTIES = "C:/work/project.properties";
	public static final String SPLASH_IMG_PATH = "C:/work/Splash_main.png";
	public static final String MAIN_IMG_PATH = "C:/work/main.png";
	public static final String ICON_IMG_PATH = "C:/work/logo.png";	
	public static final String LOGIN_IMG_PATH = "C:/work/login.png";
	public static final String ABOUT_IMG_PATH = "C:/work/img_about.png";
	
	//정규표현식
	public static final String PATTERN_ID = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";	
	public static final String PATTERN_PW = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$";	
	public static final String PATTERN_EMAIL = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";	
	public static final String PATTERN_NAME = "[가-힣]+";
	
	//프로그램 내 메뉴 타이틀
	public static final String Menu1 = "Asset Management                                                        ";	
	public static final String Menu2 = "Member Management                                                        ";	
	public static final String Menu3 = "Help                                                                    ";	
	public static final String MenuItem_ASSETINFO = "Asset Condition..                                                                                                  ";
	public static final String MenuItem_MYCUSTOMER = "My Customers...                                                                                                    ";
	public static final String MenuItem_MYINFO = "My Info..                                                                                                                    ";
	public static final String MenuItem_ABOUT = "About...                                                                                                ";
	
	//etc
	public static final String ABOUT_TEXT = "                                            Created By \n\n     KWAK YUN SEOL, PARK BAE HEUM, BAE JIN SEOP\n	LEE DONG GYU, CHOI MI RIM";
	
	
}
