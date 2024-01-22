//package Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//import com.cloudinary.*;
//import com.cloudinary.utils.ObjectUtils;
//
//
//public class TestImageUpload {
//	public static void main(String[] args) {
//		File file = new File("src\\\\Image\\\\0.jpg");
//		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//				"cloud_name", "dmuvnmqrj",
//				"api_key", "137686352499637",
//				"api_secret", "U_dqDViOWRHTiVc-LBhYWp1fWj8",
//				"secure", true));
//		try {
//			Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
