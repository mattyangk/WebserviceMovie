package model;

public class UploadFlicker {
	public static void main(String[] args){
		try {
			UploadPhoto.uploadFlicker("-u anjalpatni -s haha D:\\Extras\\wow.PNG -apiKey 70a72f7d004e6bb2684b4c65671d941c -secret a1115ff70aebd809");
			// -u anjalpatni -s haha D:\Extras\wow.PNG -apiKey 70a72f7d004e6bb2684b4c65671d941c -secret a1115ff70aebd809
			// -u yuexuanl -s haha D:\\Extras\\wow.PNG -apiKey 992c097a02257b03f4e1b7fac88fab5e -secret 85aeacf925c41b39
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
