package test.action;

import com.google.zxing.*;  
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.common.HybridBinarizer;  
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;  
  
import javax.imageio.ImageIO;  
import java.awt.*;  
import java.awt.geom.RoundRectangle2D;  
import java.awt.image.BufferedImage;  
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;  
import java.util.Random;  

public class Test1 {
	
		// 字符集
	 	private static final String CHARSET = "utf-8"; 
	 	// 图片格式 
	    private static final String FORMAT_NAME = "jpg";  
	    // 二维码尺寸  
	    private static final int QRCODE_SIZE = 300;  
	    // 条形码宽度
	    private static final int EAN_13_WIDTH = 200;
	    // 条形码高度
	    private static final int EAN_13_HEIGHT = 100;
	    // LOGO宽度  
	    private static final int WIDTH = 60;  
	    // LOGO高度  
	    private static final int HEIGHT = 60;  
	  
	    /**  
	     * 生成二维码的方法  
	     *  
	     * @param content      目标URL  
	     * @param imgPath      LOGO图片地址  
	     * @param needCompress 是否压缩LOGO  
	     * @return 二维码图片  
	     * @throws Exception  
	     */  
	    private static BufferedImage createImage(String content, String imgPath,  
	                                             boolean needCompress, int type) throws Exception {  
	    	//设置二维码纠错级别ＭＡＰ
	        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  // 矫错级别  
	        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
	        hints.put(EncodeHintType.MARGIN, 1);  
	        //创建比特矩阵(位矩阵)的QR码或条形码编码的字符串  
	        BitMatrix bitMatrix = null;
	        if(type == 1) {
	        	bitMatrix = new MultiFormatWriter().encode(content,  
		                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);  
	        }else {
	        	bitMatrix = new MultiFormatWriter().encode(content,  
		                BarcodeFormat.EAN_13, EAN_13_WIDTH, EAN_13_HEIGHT, hints);  
	        }
	        // 使BufferedImage勾画QRCode 
	        int width = bitMatrix.getWidth();  
	        int height = bitMatrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_INT_RGB);  
	        // 使用比特矩阵画并保存图像
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  
	                        : 0xFFFFFFFF);  
	            }  
	        }  
	        if (imgPath == null || "".equals(imgPath)) {  
	            return image;  
	        }  
	        // 插入图片  
	        Test1.insertImage(image, imgPath, needCompress);  
	        return image;  
	    }  
	  
	    /**  
	     * 生成条形码的方法  
	     *  
	     * @param content      目标URL  
	     * @param imgPath      LOGO图片地址  
	     * @param needCompress 是否压缩LOGO  
	     * @return 条形码图片  
	     * @throws Exception  
	     */  
		public static void ean_code(String content, String imgPath, 
				boolean needCompress) throws Exception {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  // 矫错级别  
	        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
	        hints.put(EncodeHintType.MARGIN, 1);  
	        // 条形码的格式是 BarcodeFormat.EAN_13
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
	                BarcodeFormat.EAN_13, EAN_13_WIDTH, EAN_13_HEIGHT, hints);  
			// 生成条形码图片
			File out = new File("E:" + File.separator + "ean3.png");// 指定输出路径 // File.separator解决跨平台
			Test.writeBitMatricToFile(bitMatrix, FORMAT_NAME, out);
		}
	  
	    /**  
	     * 插入LOGO  
	     *  
	     * @param source       二维码图片  
	     * @param imgPath      LOGO图片地址  
	     * @param needCompress 是否压缩  
	     * @throws Exception  
	     */  
	    private static void insertImage(BufferedImage source, String imgPath,  
	                                    boolean needCompress) throws Exception {  
	        File file = new File(imgPath);  
	        if (!file.exists()) {  
	            System.err.println("" + imgPath + "   该文件不存在！");  
	            return;  
	        }  
	        Image src = ImageIO.read(new File(imgPath));  
	        int width = src.getWidth(null);  
	        int height = src.getHeight(null);  
	        if (needCompress) { // 压缩LOGO  
	            if (width > WIDTH) {  
	                width = WIDTH;  
	            }  
	            if (height > HEIGHT) {  
	                height = HEIGHT;  
	            }  
	            Image image = src.getScaledInstance(width, height,  
	                    Image.SCALE_SMOOTH);  
	            BufferedImage tag = new BufferedImage(width, height,  
	                    BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
	            g.dispose();  
	            src = image;  
	        }  
	        // 插入LOGO  
	        Graphics2D graph = source.createGraphics();  
	        int x = (QRCODE_SIZE - width) / 2;  
	        int y = (QRCODE_SIZE - height) / 2;  
	        graph.drawImage(src, x, y, width, height, null);  
	        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
	        graph.setStroke(new BasicStroke(3f));  
	        graph.draw(shape);  
	        graph.dispose();  
	    }  
	  
	    /**  
	     * 生成二维码(内嵌LOGO)  
	     *  
	     * @param content      内容  
	     * @param imgPath      LOGO地址  
	     * @param destPath     存放目录  
	     * @param needCompress 是否压缩LOGO  
	     * @throws Exception  
	     */  
	    public static void encode(String content, String imgPath, String destPath,  
	                              boolean needCompress, int type) throws Exception {  
	        BufferedImage image = Test1.createImage(content, imgPath,  
	                needCompress, type);  
	        mkdirs(destPath);  
	        String file = new Random().nextInt(99999999) + "." + FORMAT_NAME;  
	        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));  
	    }  
	  
	    /**  
	     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)  
	     *  
	     * @param destPath 存放目录  
	     */  
	    public static void mkdirs(String destPath) {  
	        File file = new File(destPath);  
	        if (!file.exists() && !file.isDirectory()) {  
	            file.mkdirs();  
	        }  
	    }  
	  
	    /**  
	     * 生成二维码(内嵌LOGO)  
	     *  
	     * @param content  内容  
	     * @param imgPath  LOGO地址  
	     * @param destPath 存储地址  
	     * @throws Exception  
	     */  
	    public static void encode(String content, String imgPath, String destPath, int type)  
	            throws Exception {  
	        Test1.encode(content, imgPath, destPath, false, type);  
	    }  
	  
	    /**  
	     * 生成二维码  
	     *  
	     * @param content      内容  
	     * @param destPath     存储地址  
	     * @param needCompress 是否压缩LOGO  
	     * @throws Exception  
	     */  
	    public static void encode(String content, String destPath,  
	                              boolean needCompress, int type) throws Exception {  
	        Test1.encode(content, null, destPath, needCompress, type);  
	    }  
	  
	    /**  
	     * 生成二维码  
	     *  
	     * @param content  内容  
	     * @param destPath 存储地址  
	     * @throws Exception  
	     */  
	    public static void encode(String content, String destPath, int type) throws Exception {  
	        Test1.encode(content, null, destPath, false, type);  
	    }  
	  
	    /**  
	     * 生成二维码(内嵌LOGO)  
	     *  
	     * @param content      内容  
	     * @param imgPath      LOGO地址  
	     * @param output       输出流  
	     * @param needCompress 是否压缩LOGO  
	     * @throws Exception  
	     */  
	    public static void encode(String content, String imgPath,  
	                              OutputStream output, boolean needCompress, int type) throws Exception {  
	        BufferedImage image = Test1.createImage(content, imgPath,  
	                needCompress, type);  
	        ImageIO.write(image, FORMAT_NAME, output);  
	    }  
	  
	    /**  
	     * 生成二维码  
	     *  
	     * @param content 内容  
	     * @param output  输出流  
	     * @throws Exception  
	     */  
	    public static void encode(String content, OutputStream output, int type)  
	            throws Exception {  
	        Test1.encode(content, null, output, false, type);  
	    }  
	  
	    /**  
	     * 解析二维码  
	     *  
	     * @param file 二维码图片  
	     * @return  
	     * @throws Exception  
	     */  
	    public static String decode(File file) throws Exception {  
	        BufferedImage image;  
	        image = ImageIO.read(file);  
	        if (image == null) {  
	            return null;  
	        }  
	        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(  
	                image);  
	        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
	        Result result;  
	        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();  
	        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);  
	        result = new MultiFormatReader().decode(bitmap, hints);  
	        String resultStr = result.getText();  
	        return resultStr;  
	    }  
	  
	    /**  
	     * 解析二维码  
	     *  
	     * @param path 二维码图片地址  
	     * @return  不是二维码的内容返回null,是二维码直接返回识别的结果  
	     * @throws Exception  
	     */  
	    public static String decode(String path) throws Exception {  
	        return Test1.decode(new File(path));  
	    }  
	  
	    public static void main(String[] args)  {  
	  
	        // 生成二维码  
	        String text = "6923450657111";  
	        String imagePath = System.getProperty("user.dir") + "/data/1.jpg";  
	        String destPath = System.getProperty("user.dir") + "/data/output/";  
	        try {
	        	Test1.encode(text, imagePath, destPath, true, 2);  
	        }catch (Exception e){  
	            e.printStackTrace();  
	        }
	        //验证图片是否含有二维码  
	        /*String destPath1 = System.getProperty("user.dir") + "/data/3.jpg";  
	        try {  
	            String result = decode(destPath1);  
	            System.out.println(result);  
	        }catch (Exception e){  
	            e.printStackTrace();  
	            System.out.println(destPath1+"不是二维码");  
	        }  */
	    }  

}
